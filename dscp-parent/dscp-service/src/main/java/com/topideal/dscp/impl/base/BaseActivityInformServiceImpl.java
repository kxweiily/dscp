package com.topideal.dscp.impl.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.dto.request.base.BaseActivityInformReqDto;
import com.topideal.dscp.dto.response.base.BaseActivityInformRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDto;
import com.topideal.dscp.dto.response.base.PcBaseMessageCenterRespDtoSum;
import com.topideal.dscp.entity.base.BaseActivityInform;
import com.topideal.dscp.mapper.base.BaseActivityInformMapper;
import com.topideal.dscp.mapper.base.BaseMerchantMapper;
import com.topideal.dscp.service.base.BaseActivityInformService;
import com.topideal.dscp.service.base.BaseMessageCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 消息管理 - 活动通知/平台公告 ---> 接口实现类
 *
 * @author zhouhandong
 */
@Slf4j
@Service
public class BaseActivityInformServiceImpl implements BaseActivityInformService {

    @Resource
    private BaseActivityInformMapper baseActivityInformMapper;

    /**
     * 商家mapper
     */
    @Resource
    private BaseMerchantMapper baseMerchantMapper;

    @Resource
    private BaseMessageCenterService baseMessageCenterService;

    /**
     * 活动通知/平台公告 查询
     *
     * @param page                     分页参数
     * @param baseActivityInformReqDto 查询参数
     * @return
     */
    @Override
    public Page<BaseActivityInformRespDto> findPage(Page<?> page, BaseActivityInformReqDto baseActivityInformReqDto) {
        log.info("BaseActivityInformServiceImpl-->lodaPage,param--->{}",baseActivityInformReqDto);
        List<String> list = Arrays.asList(baseActivityInformReqDto.getType().split(","));
        BaseActivityInform baseActivityInform = new BaseActivityInform();
        BeanUtils.copyProperties(baseActivityInformReqDto,baseActivityInform);
        return baseActivityInformMapper.findPage(page, baseActivityInform,list);
    }

    /**
     * 活动通知/平台公告 新增
     *
     * @param baseActivityInformReqDto  新增参数
     */
    @Override
    public void insertInform(BaseActivityInformReqDto baseActivityInformReqDto) {
        log.info("BaseActivityInformServiceImpl-->insertInform,param--->{}",baseActivityInformReqDto);
        //检查数据
        checkData(baseActivityInformReqDto);

        BaseActivityInform baseActivityInform = new BaseActivityInform();
        BeanUtils.copyProperties(baseActivityInformReqDto,baseActivityInform);
        baseActivityInformMapper.insert(baseActivityInform);
    }


    /**
     * 活动通知/平台公告  审核
     *
     * @param
     * @param
     */
    @Override
    public void updateStatus(BaseActivityInform baseActivityInform) {
        log.info("BaseActivityInformServiceImpl-->updateStatus,param--->{}",baseActivityInform);
        //修改审核状态
        baseActivityInformMapper.updateById(baseActivityInform);

        //获取所有商家列表
        List<String> list = baseMerchantMapper.selectIdAll();
        //异步 批量插入数据
        baseMessageCenterService.insertAll(baseActivityInform, list);

    }


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public BaseActivityInformRespDto selectById(String id) {
        log.info("BaseActivityInformServiceImpl-->selectById,param--->{}",id);
        BaseActivityInform baseActivityInform = baseActivityInformMapper.selectById(id);
        if (baseActivityInform != null) {
            BaseActivityInformRespDto baseActivityInformRespDto = new BaseActivityInformRespDto();
            BeanUtils.copyProperties(baseActivityInform,baseActivityInformRespDto);
            return baseActivityInformRespDto;
        }
        return null;
    }

    /**
     * 更新
     *
     * @param baseActivityInformReqDto 封装对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BaseActivityInformReqDto baseActivityInformReqDto) {
        log.info("BaseActivityInformServiceImpl-->update,param--->{}",baseActivityInformReqDto);
        //检查数据
        checkData(baseActivityInformReqDto);

        BaseActivityInform baseActivityInform = new BaseActivityInform();
        BeanUtils.copyProperties(baseActivityInformReqDto,baseActivityInform);
        baseActivityInformMapper.updateById(baseActivityInform);
    }

    /**
     * 消息通知 - 系统通知查询
     *
     * @param page                     分页参数
     * @param baseActivityInformReqDto 查询参数
     * @return 结果
     */
    @Override
    public Page<BaseActivityInformRespDto> findSysPage(Page<?> page, BaseActivityInformReqDto baseActivityInformReqDto) {
        log.info("BaseActivityInformServiceImpl--->findSysPage,param--->{}", baseActivityInformReqDto);
        BaseActivityInform baseActivityInform = new BaseActivityInform();
        BeanUtils.copyProperties(baseActivityInformReqDto,baseActivityInform);
        return baseActivityInformMapper.findSysPage(page,baseActivityInform);
    }

    private void checkData(BaseActivityInformReqDto baseActivityInformReqDto) {
        if (StringUtils.isBlank(baseActivityInformReqDto.getTitle())) {
            throw new BizException("消息标题不能为空");
        }
        if (baseActivityInformReqDto.getType() == null) {
            throw new BizException("消息类型不能为空");
        }
        if (StringUtils.isBlank(baseActivityInformReqDto.getRecipient())) {
            throw new BizException("通知对象不能为空");
        }
        if (StringUtils.isBlank(baseActivityInformReqDto.getDetails())) {
            throw new BizException("消息内容不能为空");
        }
        if (baseActivityInformReqDto.getLinkType() == null) {
            throw new BizException("链接类型不能为空");
        }
        if (baseActivityInformReqDto.getSynthesize() == null) {
            throw new BizException("链接类型综合不能为空");
        }
    }
}
