package com.topideal.dscp.impl.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.common.constants.RedisConstants;
import com.topideal.dscp.common.constants.TimeConstants;
import com.topideal.dscp.common.exception.AuthenticateException;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.fieldDES.RSADESUtils;
import com.topideal.dscp.common.util.CodeGeneratorUtils;
import com.topideal.dscp.common.util.VerifyUtils;
import com.topideal.dscp.dto.request.base.BaseUserReqDto;
import com.topideal.dscp.dto.request.base.LoginReqDto;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.entity.base.*;
import com.topideal.dscp.enums.base.UserTypeEnum;
import com.topideal.dscp.interfaces.email.EmailUtils;
import com.topideal.dscp.interfaces.email.request.MailJson;
import com.topideal.dscp.interfaces.redis.RedisStringUtils;
import com.topideal.dscp.interfaces.sms.XuanwuSMSUtils;
import com.topideal.dscp.mapper.base.*;
import com.topideal.dscp.service.base.BaseUserService;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * ServiceImpl - 系统用户
 *
 * @Author: kongxj
 * @Date: 2020/6/12 14:02
 */
@Slf4j
@Service
public class BaseUserServiceImpl implements BaseUserService {

    @Resource
    private BaseUserMapper baseUserMapper;

    @Resource
    private RedisStringUtils redisStringUtils;

    @Resource
    private BaseMerchantMapper baseMerchantMapper;

    @Override
    @Transactional(readOnly = true)
    public BaseUserRespDto findById(String id) {
        BaseUser baseUser = baseUserMapper.selectById(id);
        if (baseUser != null) {
            BaseUserRespDto baseUserRespDto = new BaseUserRespDto();
            BeanUtils.copyProperties(baseUser, baseUserRespDto);
            baseUserRespDto.setUserTypeCode( baseUserRespDto.getUserType().getCode());

            // 解密
            RSADESUtils.decryptClass(baseUserRespDto);
            if(!StringUtil.isNullOrEmpty(baseUser.getEnterpriseId())){
                //获取商家名称
                BaseMerchant baseMerchant = baseMerchantMapper.selectById(baseUser.getEnterpriseId());
                if(ObjectUtils.isNotEmpty(baseMerchant)){
                    baseUserRespDto.setMerchantName(baseMerchant.getName());
                }
            }

            return baseUserRespDto;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BaseUserRespDto> findPage(Page<?> page, BaseUserReqDto baseUserReqDto) {

        // 加密参数
        RSADESUtils.encryptClass(baseUserReqDto);

        Page<BaseUserRespDto> dtos = baseUserMapper.findPage(page, baseUserReqDto);
        if (CollectionUtils.isNotEmpty(dtos.getRecords())) {
            // 解密
            RSADESUtils.decryptList(dtos.getRecords());
        }
        return dtos;
    }

    @Override
    public BaseUserRespDto findByAccount(String account) {

        // 账号加密
        account = RSADESUtils.encrypt(account);

        BaseUser baseUser = baseUserMapper.findByAccount(account);
        if (baseUser != null) {
            BaseUserRespDto baseUserRespDto = new BaseUserRespDto();
            BeanUtils.copyProperties(baseUser, baseUserRespDto);

            // 解密
            RSADESUtils.decryptClass(baseUserRespDto);

            return baseUserRespDto;
        }
        return null;
    }

    @Override
    @Transactional
    public String save(BaseUser baseUser) {
        // 加密
        RSADESUtils.encryptClass(baseUser);

        baseUserMapper.insert(baseUser);
        return baseUser.getId();
    }

    @Override
    @Transactional
    public Boolean update(BaseUser baseUser) {
        // 加密
        RSADESUtils.encryptClass(baseUser);
        return baseUserMapper.updateById(baseUser) == 1;
    }

    @Override
    @Transactional
    public Boolean delete(String id) {
        return baseUserMapper.deleteById(id) == 1;
    }

    @Override
    public void sendVerifyCode(String account) {
        if (StringUtils.isBlank(account)) {
            throw new BizException("验证码发送失败, 账号不能为空！");
        }

        //        BaseUserRespDto userDto = this.findByAccount(account);
        //        if (userDto == null || userDto.getId() == null) {
        //            throw new BizException("验证码发送失败, 用户不存在！");
        //        }

        // 当前用户验证码发送次数 连续1小时超过5次则返回失败
        // 分组key：SMS_VERIFY_CODE_SENDNUM_LIMIT:用户id
        String smsSendNumkey = RedisConstants.SMS_VERIFY_CODE_SENDNUM_LIMIT + account;
        Integer smsSendNum = 0;
        if (redisStringUtils.hasKey(smsSendNumkey)) {
            smsSendNum = Integer.valueOf(redisStringUtils.get(smsSendNumkey).toString());
            if (smsSendNum >= 5) {
                throw new BizException("验证码发送失败, 当前用户验证码发送次数过多，请1小时后再尝试！");
            }
        }

        // 生成 6位随机验证码
        String code = CodeGeneratorUtils.generateVerifyCode();
        // 手机格式 发手机短信
        if (VerifyUtils.isMobile(account)) {
            // TODO 短信模板

            // 发送短信
            XuanwuSMSUtils.send("dscp验证码：" + code, account);

            // 邮箱格式 发邮件验证
        } else if (VerifyUtils.isEmail(account)) {
            // TODO 邮件模板

            MailJson mail = new MailJson();
            mail.setFrom("xiangjie.kong@topideal.com");
            mail.setPassword("");
            mail.setTo(new String[]{account});
            mail.setSubject("dscp 验证码");
            mail.setText("dscp验证码：" + code + " 请查收");

            try {
                EmailUtils.sendEmail(mail);
            } catch (Exception e) {
                throw new BizException("邮件验证码发送失败, " + e.getMessage());
            }
        } else {
            throw new BizException("验证码发送失败, 账号格式验证失败");
        }

        // 账户验证码 存到redis中 5分钟
        // 分组key：SMS_VERIFY_CODE:用户id
        String key = RedisConstants.SMS_VERIFY_CODE + account;
        redisStringUtils.set(key, code, TimeConstants.VERIFY_CODE_EXPIRE_TIME);

        // 记录当前用户发送验证码次数
        smsSendNum++;
        redisStringUtils.set(smsSendNumkey, smsSendNum.toString(), TimeConstants.VERIFY_CODE_NUMBER_LIMIT_TIME);
    }

    @Override
    public boolean checkVerifyCode(String account, String curVerifyCode) {
        if (StringUtils.isBlank(account) || StringUtils.isBlank(curVerifyCode)) {
            return false;
        }
        // 校验 redis 中的 verifyCode
        // 存在未过期 且与 请求参数verifyCode 一致
        // 分组key：SMS_VERIFY_CODE:用户账号
        String key = RedisConstants.SMS_VERIFY_CODE + account;
        Object verifyCode = redisStringUtils.get(key);
        if (Objects.isNull(verifyCode)
                || !curVerifyCode.equals(verifyCode)) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(LoginReqDto loginReqDto) {

        String account = loginReqDto.getAccount(); // 输入的用户名
        String password = loginReqDto.getPassword(); // 输入的密码
        String verifyCode = loginReqDto.getVerifyCode(); // 验证码

        // 查询用户账号是否存在
        if (this.findByAccount(loginReqDto.getAccount()) != null) {
            throw new BizException("用户注册失败, 账号已存在！");
        }

        // 验证验证码
        if (!this.checkVerifyCode(account, verifyCode)) {
            throw new AuthenticateException("用户注册失败, 验证码验证失败!");
        }

        // 构建用户实体保存
        BaseUser baseUser = new BaseUser();
        // 手机格式
        if (VerifyUtils.isMobile(account)) {
            baseUser.setTel(account);
            // 邮箱格式
        } else if (VerifyUtils.isEmail(account)) {
            baseUser.setEmail(account);
        } else {
            throw new BizException("用户注册失败, 账号格式验证失败");
        }
        baseUser.setPassword(password);
        baseUser.setUserType(UserTypeEnum.BASIC);

        String userId = this.save(baseUser);

        // TODO 注册成功后 异步签署协议
        if (StringUtils.isNotBlank(userId)) {

        }


        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(LoginReqDto loginReqDto) {
        String account = loginReqDto.getAccount(); // 输入的用户名
        String password = loginReqDto.getPassword(); // 输入的密码
        String verifyCode = loginReqDto.getVerifyCode(); // 验证码

        // 查询用户
        BaseUserRespDto baseUserRespDto = this.findByAccount(account);

        // 查无此用户
        if (baseUserRespDto == null || baseUserRespDto.getId() == null) {
            throw new AuthenticateException("用户密码重置失败, 账号不存在!");
        }

        // 按验证码验证
        if (!this.checkVerifyCode(account, verifyCode)) {
            throw new AuthenticateException("用户密码重置失败, 验证码验证失败!");
        }

        // 更新用户密码
        BaseUser baseUser = new BaseUser();
        baseUser.setId(baseUserRespDto.getId());
        baseUser.setPassword(password);

        return this.update(baseUser);
    }

}
