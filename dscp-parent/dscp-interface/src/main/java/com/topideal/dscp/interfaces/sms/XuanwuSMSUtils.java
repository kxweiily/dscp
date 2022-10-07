package com.topideal.dscp.interfaces.sms;

import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson2.JSONObject;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.util.MD5Utils;
import com.topideal.dscp.interfaces.config.ConfigSettings;
import com.topideal.dscp.interfaces.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 玄武短信发送 工具类
 * @author kxj
 * 2022年3月2日14:55:35
 */
public class XuanwuSMSUtils {

    private static final Logger log = LoggerFactory.getLogger(XuanwuSMSUtils.class);

    private static final String DEF_REGEX = "\\$\\{(.+?)\\}";

    /**
     * 初始化 header
     * @return
     */
    private static Map init() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type","application/json;charset=utf-8");
        header.put("Accept","application/json");
        header.put("Authorization",authorization()); // 加密
        return header;
    }

    /**
     * 短信发送方法
     * @param content           短信模板
     * @param parameter         模板参数
     * @param tels              发送手机  多个","分隔
     * @return
     */
    public static String send(String content, Map<String, Object> parameter, String tels){
        if (StringUtils.isBlank(tels)) {
            log.info("玄武短信发送 手机号为空！");
            return null;
        }

        // 初始化header
        Map header = init();
        // 拼接短信发送内容
        content = render(content, parameter, DEF_REGEX);

        //创建uuid作为customMsgId
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取电话号码 多个","分隔
        String[] tel = tels.split(",");
        JSONArray telArray = new JSONArray();
        for (int i = 0; i < tel.length; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("to", tel[i]);
            telArray.add(jsonObject);
        }

        //封装请求体
        JSONObject json = new JSONObject();
        json.put("items",telArray);
        json.put("content",content);
        json.put("msgType","sms");
        json.put("customMsgId",uuid);
        String resultMsg= HttpClientUtil.doPost(ConfigSettings.getXuanwuUrl(), json.toString(), header);
        log.info("玄武短信发送 ==== 请求报文：{}  ==== 响应报文：{}", json.toString(), resultMsg);
        return resultMsg;
    }

    /**
     * 短信发送方法
     * @param content           发送内容
     * @param tels              发送手机  多个","分隔
     * @return
     */
    public static String send(String content, String tels){
        if (StringUtils.isBlank(tels)) {
            throw new BizException("玄武短信发送 手机号为空！");
        }

        // 初始化header
        Map header = init();

        //创建uuid作为customMsgId
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取电话号码 多个","分隔
        String[] tel = tels.split(",");
        JSONArray telArray = new JSONArray();
        for (int i = 0; i < tel.length; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("to", tel[i]);
            telArray.add(jsonObject);
        }

        //封装请求体
        JSONObject json = new JSONObject();
        json.put("items",telArray);
        json.put("content",content);
        json.put("msgType","sms");
        json.put("customMsgId",uuid);
        String resultMsg= HttpClientUtil.doPost(ConfigSettings.getXuanwuUrl(), json.toString(), header);
        log.info("玄武短信发送 ==== 请求报文：{}  ==== 响应报文：{}", json.toString(), resultMsg);
        return resultMsg;
    }

    /**
     * 替换模板 ${} 参数 工具类
     * @param template
     * @param parameter
     * @param regex
     * @return
     */
    private static String render(String template, Map<String, Object> parameter, String regex){
        if (parameter == null || parameter.size() < 1) {
            return template;
        }

        try {
            StringBuffer appendReplaceSb = new StringBuffer();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(template);

            while (matcher.find()) {
                // String currentStr = matcher.group(0);//整个正则匹配的字符串
                String key = matcher.group(1);//当前第一个子分组正则匹配的字符串
                String value = parameter.get(key).toString();

                 /*Matcher. appendReplacement：将当前匹配的子字符串替换为指定的字符串，并且将替换后的字符串
                及其之前到上次匹配的子字符串之后的字符串添加到一个StringBuffer对象中。*/
                matcher.appendReplacement(appendReplaceSb, value);//
            }


            /*   Matcher. appendTail：将最后一次匹配之后的字符串添加到一个StringBuffer对象中。*/
            matcher.appendTail(appendReplaceSb);
            return appendReplaceSb.toString();
        } catch (Exception e) {
            log.error("玄武短信发送失败 短信模板设置不正确");
            e.printStackTrace();
        }

        return template;
    }

    /**
     * 编辑请求头的加密参数
     * @return
     */
    private static String authorization(){
        try {
            //使用Base64(用户名 + 冒号 + MD5(密码))
            String userName = ConfigSettings.getXuanwuUserName();
            //通过md5加密密码
            String password = MD5Utils.MD5(ConfigSettings.getXuanwuPassword());
            //通过Base64加密
            byte[] auth = Base64.getEncoder().encode((userName + ":" + password).getBytes());
            return new String(auth,"utf-8");
        } catch (Exception e) {
            log.info("加密authorization失败");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        String template = "测试啦 编号：${code} 的人员 在 ${action}";
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("code", "aA001");
        parameter.put("action", "打游戏");
        String a = render(template, parameter, DEF_REGEX);

        System.out.println(a);

        Map<String, Object> parameter1 = new HashMap<>();
        parameter1.put("code", "as24c13");

//        send(MessageCodeEnum.NOTE_E001, parameter1, "13790012221");
    }


}
