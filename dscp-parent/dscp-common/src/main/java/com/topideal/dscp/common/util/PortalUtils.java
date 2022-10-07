package com.topideal.dscp.common.util;



import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PortalUtils {

	private PortalUtils() {
		super();
	}

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalUtils.class);

    /**
     * 判断时间是否失效
     * @param timestamp
     * @return
     * @throws ParseException
     */
    public static Boolean betweenTime(String timestamp) throws ParseException {
        Boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date oldDate = sdf.parse(timestamp);
        Date newDate = new Date();
        Long between  = newDate.getTime()-oldDate.getTime();
        if(between <= 60*30*1000) {
            result = true;
        }
        return result;
    }



    /**
     * 获取页面参数
     * @return
     */
    public static JSONObject getJsonKey(String json, String app_id, String method, String version, String timestamp){
            JSONObject jsonObject=  JSONObject.parseObject(json);
            jsonObject.put("app_key", app_id);
            jsonObject.put("method", method);
            jsonObject.put("v", version);
            jsonObject.put("timestamp", timestamp);
            return jsonObject;
    }

    /**
     * 生成要请求给E速达的参数
     * @param jsonObject 请求前的SON对象
     * @param key appkey
     * @return 要请求的参数
     */
    public static String buildRequestPara(JSONObject jsonObject,String key) {
        //除去数组中的空值和签名参数
        Map<String, Object> sPara = paraFilter(jsonObject);
        //生成签名结果
        String mysign = buildRequestMysign(sPara,key);
        return mysign;
    }


    /**
     * 除去数组中的空值和签名参数
     * @param jsonObject 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, Object> paraFilter(JSONObject jsonObject) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (jsonObject == null || jsonObject.size() <= 0) {
            return result;
        }
        for (String key : (Set<String>) jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if(value instanceof String){
                if(StringUtils.isNotBlank((String)value)&&!"sign".equals(key)){
                    result.put(key, value);
                }
            }else if(value instanceof List){
                if(value!=null&&((List) value).size()!=0){
                    result.put(key, value);
                }
            }else{
                if(value!=null){
                    result.put(key, value);
                }
            }
        }
        return result;
    }




    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, Object> sPara,String key) {
        String prestr = createLinkString(sPara);
        String mysign=sign(prestr, key, "utf-8");
        return mysign;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys, Collator.getInstance(Locale.ENGLISH));
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);
            prestr = prestr + key + value;
        }
        return prestr;
    }

    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        text = key+text+key;
        return Encode32MD5(text,input_charset);
    }


    public static String Encode32MD5(String myinfo,String input_charset) {
        byte[] digesta = null;
        try {
            byte[] btInput = myinfo.getBytes(input_charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            digesta = mdInst.digest();
        }
        catch (Exception ex) {
            LOGGER.error("--------------------MD5加密失败-------------------");
        }
        return byte2hex(digesta);
    }

    public static String byte2hex(byte[] b) {
        StringBuilder md5Str = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF)).toUpperCase();
            if (stmp.length() == 1) {
                md5Str.append(0 + stmp);
            } else {
                md5Str.append(stmp);
            }
            if (n < b.length - 1)
            {md5Str.append("");}
        }
        return md5Str.toString();
    }

}
