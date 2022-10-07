package com.topideal.dscp.common.exception;

/**
 * 数据安全性异常
 */
public class DataDisorderException extends GlobalException{

    private static final long serialVersionUID = 1567952158062123L;

    public DataDisorderException(int code, String message) {
        super(code, message);
    }
}
