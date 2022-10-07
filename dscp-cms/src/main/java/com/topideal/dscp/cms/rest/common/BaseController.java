package com.topideal.dscp.cms.rest.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.cms.config.security.DscpUser;
import com.topideal.dscp.common.fieldDES.DataMaskingUtils;
import com.topideal.dscp.common.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;

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

	/**
	 * 获取当前登陆用户封装信息
	 * @return ISVUser
	 */
	protected final DscpUser getCurrent() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (object instanceof DscpUser) {
			return (DscpUser)object;
		}
		return null;
	}

	/**
	 * 获取当前登陆 用户id
	 * @return
	 */
	protected final String getCurrentUserId() {
		return this.getCurrent() != null ? this.getCurrent().getUserId() : null;
	}


	/**
	 * 获取当前登陆 账户类型   0:系统用户   1:普通用户
	 * @return
	 */
	protected Integer getCurrentAccountType() {
		return this.getCurrent() != null ? this.getCurrent().getAccountType() : null;
	}

	/**
	 * 获取当前登陆 普通用户绑定商家id
	 * @return
	 */
	protected final String getCurrentEnterpriseId() {
		return this.getCurrent() != null ? this.getCurrent().getEnterpriseId() : null;
	}

	/**
	 * 获取当前登陆 用户是否有数据权限
	 * @return
	 */
	protected final Boolean getCurrentHasDataAuthority() {
		return this.getCurrent() != null ? this.getCurrent().getHasDataAuthority() : Boolean.FALSE;
	}


	/**
	 * 返回数据脱敏处理
	 * @param obj
	 * @return
	 */
	protected Message MessageForDMData(Object obj) {
		if (Objects.isNull(obj)) return SUCCESS_MESSAGE;
		// 根据有没数据权限判断
		// 没数据权限 需要脱敏
		if (!getCurrentHasDataAuthority()) {
			// obj 不同类型 对应 脱敏处理方法
			if (obj instanceof List) {
				DataMaskingUtils.executeList((List)obj);

			} else if (obj instanceof Page) {
				DataMaskingUtils.executeList(((Page) obj).getRecords());

			} else {
				DataMaskingUtils.execute(obj);
			}
		}

		return Message.success(obj);
	}

}
