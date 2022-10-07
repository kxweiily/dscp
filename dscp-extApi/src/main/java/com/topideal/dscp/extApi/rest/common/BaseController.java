package com.topideal.dscp.extApi.rest.common;


import com.topideal.dscp.common.vo.Message;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller - 基类
 * 
 * @author kongxj
 * @version 1.0
 */
@Slf4j
public abstract class BaseController {

	/** 成功消息 */
	protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");

	/** 错误消息 */
	protected static final Message ERROR_MESSAGE = Message.error("操作失败");



}
