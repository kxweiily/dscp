package com.topideal.dscp.cms.config;

import com.topideal.dscp.common.exception.AuthenticateException;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * @Author: kongxj
 * @Date: 2022/7/18 17:11
 */
@Slf4j
@RestControllerAdvice  //捕获全局异常
public class GlobalExceptionHandler {

    /**
     * 业务类 异常
     * @param e
     * @return  返回状态0 前端统一处理
     */
    @ExceptionHandler(value = BizException.class)
    public Message bizExceptionHandler(BizException e) {
        log.error("发生业务异常! 原因是：{}", e.getMessage());
        return new Message(Message.CodeEnum.ERROR.getStatus(), e.getMessage());
    }

    /**
     * 认证类 异常
     * @param e
     * @return  返回状态411 前端统一处理
     */
    @ExceptionHandler(value = AuthenticateException.class)
    public Message authenticateExceptionHandler(AuthenticateException e) {
        log.error("用户认证异常! 原因是：{}", e.getMessage());
        return new Message(Message.CodeEnum.ERROR.getStatus(), e.getMessage());
    }

    /**
     * 未知 异常
     * @param e
     * @return  返回状态500 前端统一处理
     */
    @ExceptionHandler(value = Exception.class)
    public Message exceptionHandler(Exception e) {
        log.error("未知异常! 原因是：{}", e.getMessage());
        e.printStackTrace();
        return new Message(Message.CodeEnum.SYS_ERROR_500.getStatus(), Message.CodeEnum.SYS_ERROR_500.getContent());
    }

}
