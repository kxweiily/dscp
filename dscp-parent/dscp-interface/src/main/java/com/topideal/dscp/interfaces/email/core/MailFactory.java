package com.topideal.dscp.interfaces.email.core;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件工厂
 * 创建 javaMailSender 实例
 */
@Component
public class MailFactory {

    /**
     * 获取JavaMailSender 实例
     * @param username
     * @param password
     * @return
     */
    public static JavaMailSender sender(String username,String password){
        JavaMailSender javaMailSender=null;
        if(username.endsWith("@163.com")){
            javaMailSender=new Mail163Sender().instanceSender(username,password);
        }else if(username.endsWith("@qq.com")){
            javaMailSender=new QQMailSender().instanceSender(username,password);
        }else if(username.endsWith("@sina.cn")){
            javaMailSender=new SinaMailSender().instanceSender(username,password);
        } else {
            javaMailSender=new ExmailSender().instanceSender(username,password);
        }
         return javaMailSender;
    }


    public static void main(String[]  args)throws MessagingException,Exception {
        JavaMailSender sender = MailFactory.sender("18588697580m@sina.cn", "wl123456");
        //JavaMailSender sender = MailFactory.getJavaMailSender("tvrcihtydddf@163.com", "18588697580","002");
        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
        //发件人
        helper.setFrom("18588697580m@sina.cn");
        //设置收件人
        helper.setTo("xiaolei.wei@topideal.com.cn");
        helper.setSubject("测试");
        helper.setText("我不想说，你知道的", true);
        sender.send(msg);
        System.out.println("发送成功");
    }

}
