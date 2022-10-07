package com.topideal.dscp.interfaces.utils;


import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by acer on 2016/7/5.
 */
public class HttpClientUtil {

    //===============header 版本======================

    /**
     * 发送Post 请求
     *
     * @param url  地址
     * @param json json数据
     * @return 回执报文
     */
    public static String doPost(String url, String json, Map<String, String> header) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            //header 请求头中注入参数
            for (String key : header.keySet()) {
                httpPost.setHeader(key, header.get(key));
            }
            if (StringUtils.isNotBlank(json)) {
                StringEntity s = new StringEntity(json, "utf-8");  // 中文乱码在此解决
                s.setContentType("application/json");
                httpPost.setEntity(s);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }


    /**
     * 发送Post 请求
     *
     * @param url     地址
     * @param json    json数据
     * @param charset 编码
     * @return 回执报文
     */
    public static String doPost(String url, String json, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            if (StringUtils.isNotBlank(json)) {
                StringEntity s = new StringEntity(json, charset);  // 中文乱码在此解决
                s.setContentType("application/json");
                httpPost.setEntity(s);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return result;

    }

    /**
     * 发送Post 请求
     *
     * @param url     地址
     * @param json    json数据
     * @param charset 编码
     * @return 回执报文
     */
    public static String doPost(String url, JSONObject json, String charset) {
        HttpPost httpPost = null;
        String result = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
            Set<String> keys = json.keySet();
            if (CollectionUtils.isNotEmpty(keys)) {
                for (String key : keys) {
                    String value = json.getString(key);
                    pairList.add(new BasicNameValuePair(key, value));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
            HttpResponse response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return result;

    }


    /**
     * @param url
     * @param json
     * @param charset
     * @param header  请求头参数
     * @return
     */
    public static String doPost(String url, String json, String charset, Map<String, String> header) {
        HttpPost httpPost = null;
        String result = null;
        try {
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            if (header != null) {
                for (Map.Entry<String, String> entry : header.entrySet()) {
                    httpPost.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if (StringUtils.isNotBlank(json)) {
                StringEntity s = new StringEntity(json, charset);  // 中文乱码在此解决
                s.setContentType("application/json");
                httpPost.setEntity(s);
            }
            HttpResponse response = new DefaultHttpClient().execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return result;

    }

    /**
     * 发送Post 请求
     *
     * @param url     地址
     * @param xml     xml格式的数据
     * @param charset 编码
     * @return 回执报文
     */
    public String doPostXml(String url, String xml, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(xml));
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }

    /**
     * 发送Get 请求
     *
     * @param url     地址
     * @param charset 编码
     * @return 回执报文
     */
    public static String doGetReq(String url, String charset) {
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return result;

    }

    public static void main(String[] args) {
        String url = "http://121.33.205.117:18080/customs/rest/custjson/addOrder.do";
        String json = "{  \"orderId\": \"TH2016050100088\",  \"orderDate\": \"2016-06-20 16:06:31\",  \"customerId\": \"1000000167\",  \"tpl\": \"1000823\",  \"electricCode\": \"1000000167\",  \"cbepcomCode\": \"1000000167\",  \"orderType\": 1,  \"customsType\": 2,  \"freightFcy\": 45.0000000000,  \"freightFcode\": \"CNY\",  \"goodList\": [    {      \"gnum\": 1,      \"goodId\": \"EGSCMATMD\",      \"amount\": 2.0,      \"price\": 12.0000000000    }  ],  \"recipient\": {    \"name\": \"关一日\",    \"mobilePhone\": \"1008610086\",    \"phone\": \"\",    \"country\": \"中国\",    \"city\": \"广州市\",    \"province\": \"广东省\",    \"address\": \"TH2016050100002\"  }}";
        HttpClientUtil util = new HttpClientUtil();
        System.out.println(util.doPost(url, json, "utf-8"));

    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
