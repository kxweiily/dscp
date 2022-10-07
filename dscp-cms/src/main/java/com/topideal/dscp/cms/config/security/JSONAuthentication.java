package com.topideal.dscp.cms.config.security;

import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Json 返回处理类
 * @Author: kongxj
 * @Date: 2022/7/16 14:21
 */
public class JSONAuthentication {

    /**
     * 输出JSON
     *
     * @param request
     * @param response
     * @param obj
     * @throws IOException
     * @throws ServletException
     */
    public static void WriteJSON(HttpServletRequest request,
                             HttpServletResponse response,
                             Object obj) throws IOException, ServletException {
        //这里很重要，否则页面获取不到正常的JSON数据集
        response.setContentType("application/json;charset=UTF-8");

        //跨域设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Method", "POST,GET");
        //输出JSON
        PrintWriter out = response.getWriter();
        out.write(JSON.toJSONString(obj));
        out.flush();
        out.close();
    }
}
