package com.topideal.dscp.interfaces.email.request;

import java.util.Map;

/**
 * 发送邮件报文
 */
public class MailJson{

    //发件人/账号
    private String from;
    //密码
    private String password;
    //收件人
    private String[] to;
    //抄送
    private String[] cc;
    //暗送
    private String[] bcc;
    //回复邮件地址
    private String replyTo;
    //标题
    private String subject;
    //邮件正文件
    private String text;
    //发件人 别名
    private String alias;
    //附件  (附件名,base64(byte[]))
    private Map<String,String> files;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
