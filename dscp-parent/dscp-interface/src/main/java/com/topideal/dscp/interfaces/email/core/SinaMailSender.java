package com.topideal.dscp.interfaces.email.core;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 发送新浪邮件
 */
public class SinaMailSender implements MailSender {


    @Override
    public JavaMailSender instanceSender(String username, String password) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.sina.cn");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setPort(465);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        //加认证机制
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.setProperty("mail.smtp.socketFactory.port", Integer.toString(465));//设置ssl端口
        javaMailProperties.put("mail.smtp.socketFactory.fallback", false);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.timeout", 5000);
        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }
}
