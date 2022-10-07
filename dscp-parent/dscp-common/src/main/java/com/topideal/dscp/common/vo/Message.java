package com.topideal.dscp.common.vo;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Vo - 消息
 *
 * @author sunjian
 * @version 1.0
 */
public final class Message implements Serializable {

    private static final long serialVersionUID = 7591016710887863979L;

    /**
     * 响应状态码
     * lizhenni
     */
    public enum CodeEnum {
        SUCCESS(200, "成功"),
        ERROR(0, "错误"),
        ERROR_101(101,"请求参数异常"),
        ERROR_102(102,"sql操作异常"),
        ERROR_103(103,"数据不存在"),
        ERROR_104(104,"更新脏数据"),
        ERROR_105(105,"删除被关联的数据"),
        ERROR_106(106,"数据已存在"),
        //--------------------------系统错误------------------------------------
        SYS_ERROR_411(411, "Token失效, 请重新登录"),
        SYS_ERROR_401(401, "资源无访问权限"),
        SYS_ERROR_405(405, "非法请求"),
        SYS_ERROR_500(500, "未知错误");

        CodeEnum(int status, String content){
            this.status = status;
            this.content = content;
        }
        //状态码
        private int status;
        //状态标识
        private String content;

        public int getStatus() {
            return status;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return content;
        }
    }


    /** 状态 */
    private int status;

    /** 内容 */
    private Object content;

    public Message() {
        super();
    }

    /**
     * @param status 状态
     */
    public Message(int status) {
        this(status, null);
    }

    /**
     * @param codeEnum  类型码
     */
    public Message(CodeEnum codeEnum) {
        this(codeEnum.getStatus(), codeEnum.content);
    }

    /**
     * @param status 状态
     * @param content 内容
     */
    public Message(int status, Object content) {
        this.status = status;
        if (content == null) content = "";
        this.content = content;
    }


    /**
     * 返回成功消息
     *
     * @param content 内容
     * @return 成功消息
     */
    public static Message success(Object content) {
        return new Message(CodeEnum.SUCCESS.getStatus(), content);
    }

    /**
     * 返回错误消息
     *
     * @param content 内容
     * @return 错误消息
     */
    public static Message error(Object content) {
        return new Message(CodeEnum.ERROR.getStatus(), content);
    }


    /**
     * @return 状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status 状态
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return 内容
     */
    public Object getContent() {
        return content;
    }

    /**
     * @param content 内容
     */
    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content == null ? StringUtils.EMPTY : content.toString();
    }

}
