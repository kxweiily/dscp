package com.topideal.dscp.cms.rest.common;

import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.vo.Message;
import com.topideal.dscp.interfaces.awsSdk.S3Utils;
import com.topideal.dscp.service.base.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 公共 接口类
 * @Author: kongxj
 * @Date: 2022/8/9 15:59
 */
@Slf4j
@RestController
@RequestMapping("/cms/common")
public class CommonController extends BaseController {

    @Resource
    private BaseUserService baseUserService;

    /**
     * 发送验证码
     * @param account 账号
     * @return
     * @throws
     */
    @GetMapping(value = "/sendVerifyCode")
    public Message sendVerifyCode(String account) {

        if (StringUtils.isBlank(account)) {
            throw new BizException("账号不能为空!");
        }

        // 发送验证码
        baseUserService.sendVerifyCode(account);

        return SUCCESS_MESSAGE;
    }

    /**
     * 上传图片
     * @param file 图片文件
     * @return
     * @throws
     */
    @PostMapping(value = "/imgUpload")
    public Message imgUpload(@RequestParam MultipartFile file) throws IOException {

        if (file == null) {
            throw new BizException("图片文件为空!");
        }

        String ofileName = file.getOriginalFilename();
        // 图片后缀
        String fileSuffix = ofileName.substring(ofileName.lastIndexOf(".") + 1, ofileName.length());

        return Message.success(S3Utils.uploadImg(file.getInputStream(), fileSuffix));
    }

    /**
     * 上传文档
     * @param file 文档文件
     * @return
     * @throws
     */
    @PostMapping(value = "/fileUpload")
    public Message fileUpload(@RequestParam MultipartFile file) throws IOException {

        if (file == null) {
            throw new BizException("文件为空!");
        }

        String ofileName = file.getOriginalFilename();
        // 图片后缀
        String fileSuffix = ofileName.substring(ofileName.lastIndexOf(".") + 1, ofileName.length());

        return Message.success(S3Utils.uploadFile(file.getInputStream(), fileSuffix));
    }


}
