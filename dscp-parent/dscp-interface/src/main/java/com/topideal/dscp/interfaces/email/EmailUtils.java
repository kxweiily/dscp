package com.topideal.dscp.interfaces.email;

import java.util.Base64;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.topideal.dscp.interfaces.email.core.MailFactory;
import com.topideal.dscp.interfaces.email.request.MailJson;

public class EmailUtils {
	
	private static final Logger LOGGER= LoggerFactory.getLogger(EmailUtils.class);
	/**
	 * 发送邮件
	 * 附件格式为：字节数组base64码
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public static String sendEmail(MailJson model) throws Exception{
        JavaMailSender sender=MailFactory.sender(model.getFrom(),model.getPassword());
        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
        //发件人
        String from=model.getFrom();
        if(StringUtils.isNotBlank(model.getAlias())){
            String nick=javax.mail.internet.MimeUtility.encodeText(model.getAlias());
            from = new StringBuilder().append(nick)
                    .append(" <").append(from).append(">")
                    .toString();
        }
        helper.setFrom(from);
        //设置收件人
        helper.setTo(model.getTo());
        //回复地址
        if (StringUtils.isNotBlank(model.getReplyTo())) {
            helper.setReplyTo(model.getReplyTo());
        }
        //抄送地址
        if (model.getCc()!=null&&model.getCc().length>=1) {
            helper.setCc(model.getCc());
        }
        //暗送地址
        if (model.getBcc()!=null&&model.getBcc().length>=1) {
            helper.setBcc(model.getBcc());
        }
        helper.setSubject(model.getSubject());
        helper.setText(model.getText(), true);
        //添加 附件
        if(model.getFiles()!=null&&model.getFiles().size()>0){
            for (Map.Entry<String, String> stringStringEntry : model.getFiles().entrySet()) {
            	// 解码，然后将字节转换为InputStream
                byte[] bytes = Base64.getDecoder().decode(stringStringEntry.getValue());   //将字符串转换为byte数组
                helper.addAttachment(stringStringEntry.getKey(),new ByteArrayResource(bytes));
            }
        }
        sender.send(msg);
        LOGGER.info("推送邮件服务器成功！！！");
        return "发送邮件成功";
	}
	
}
