package com.topideal.dscp.common.util;

/**
 * 常量类
 *
 * @author fengchognshu
 */
public class MegConstants {
    //失败提示常量
    public static final String ERROR_HAVE_CHARS = "模板编码不能包含中文";
    public static final String ERROR_HAVE_CODE = "模板编码已存在";
    public static final String ERROR_CODE_EMPTY = "模板编码不能为空";
    public static final String ERROR_NAME_EMPTY = "姓名不能为空";
    public static final String ERROR_MERCHANT_EMPTY = "企业名称不能为空";
    public static final String ERROR_MERCHANT_NULL = "主数据客商初始化接口响应结果为空";
    public static final String ERROR_MERCHANT_STATUS = "请求主数据客商初始化接口失败";
    public static final String ERROR_CONT_STATUS = "请求主数据数字履约初始化接口失败";
    public static final String ERROR_CONT_NULL = "主数据数字履约初始化接口响应结果为空";

    //查询MDM数据常量 //查询oa数据常量
    public static final String STATUS = "status";
    public static final String TIS = "TIS";
    public static final String NOTES = "notes";
    public static final String TENANTCODE = "zhuozhi";
    public static final int TWO = 2;
    public static final int PAGE = 1;
    public static final int PAGESIZE = 1000;
    public static final String TOTAL = "total";
    public static final String DATA = "data";
    public static final String BODY="body";
    public static final String MESSAGE="message";



    //线程常量
    public static final int RUNSIZE = Runtime.getRuntime().availableProcessors() > 10 ? Runtime.getRuntime().availableProcessors() : 10;

    //调拨出入单
    public static final String MAP_KEY_TOTALNUM="totalNum";
    public static final String MAP_KEY_TOTALMONEYS="totalMoneys";
    public static final String MAP_KEY_GOODS="totalGoods";


    public static final String OK="OK";
    public static final String ERROR="ERROR";
}
