package com.topideal.dscp.interfaces.redis;

/**
 * 定义存放redis数据的key
 *
 */
public enum RedisKeyEnum {
    //意见反馈邮件发件人信息
	GIVEFEEDBACK_MAIL_ACCOUNT("GIVEFEEDBACKMAILACCOUNT","意见反馈邮件发件人信息"),
   
	;
	
	
	private String key;
	
	private String name;
	
	private RedisKeyEnum(String key,String name) {
		this.key = key;
		this.name = name;
	}
	
	public String getKey() {
		return key;
	}
	public String getName() {
		return name;
	}
	
	
}
