package com.topideal.dscp.interfaces.email.core;

import org.springframework.mail.javamail.JavaMailSender;

/**
 * 发送邮件客户端
 */
public interface MailSender {


    public JavaMailSender instanceSender(String username,String password);







}
