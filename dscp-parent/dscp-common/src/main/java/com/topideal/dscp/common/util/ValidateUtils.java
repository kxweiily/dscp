package com.topideal.dscp.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 接口校验工具类
 * @author Chen
 *
 */
public class ValidateUtils{
	/**
	 * 用于校验接口签名的参数
	 * @param param     请求参数
	 * @param secretKey 秘钥
	 * @param urlSign 请求签名
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> validateSign(JSONObject param, String secretKey,String urlSign ) throws Exception{
		Map<String, String> msg = new HashMap<String,String>();
        if (StringUtils.isBlank(urlSign)) {
        	msg.put("status", "2");
        	msg.put("notes", "签名字段不能为空");
			return msg;
		}
        String sign = PortalUtils.buildRequestPara(param,secretKey);
        if (!urlSign.equals(sign)) {
        	msg.put("status", "2");
        	msg.put("notes", "签名错误");
			return msg;
		}
        msg.put("status", "1");
		return msg;
	}




}
