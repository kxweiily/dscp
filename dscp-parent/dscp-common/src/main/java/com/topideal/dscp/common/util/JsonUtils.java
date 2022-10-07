package com.topideal.dscp.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 标题: json工具类
 * @Author: lanjy
 * @Date: 2020/8/27 16:35
 **/
public class JsonUtils {


	/**
	 * 将json数据中的下划线命名的Key取代为驼峰命名的key(方便将json数据转换成实体对象)
	 * @param jsonStr
	 * @return
	 */
	public static String jsonKeyEdit(String jsonStr){

		//转换后的字符串
		String str = jsonStr;

		//满足json字符串key的正则
		String regx = "\"\\w+\":";

		//1.将正在表达式封装成对象Patten 类来实现
		Pattern pattern = Pattern.compile(regx);

		//2.将字符串和正则表达式相关联
		Matcher matcher = pattern.matcher(jsonStr);

		//查找符合规则的子串
		while(matcher.find()){

			//获取的字符串的首位置和末位置 以及 获取 字符串
//			System.out.println(matcher.start()+"--"+matcher.end()+" : "+matcher.group());
			str = str.replaceFirst(matcher.group(),camelCase(matcher.group()));
		}

		return str;
	}

	/**
	 * 下划线命名转驼峰命名
	 * @param str
	 * @return
	 */
	public static String camelCase(String str){
		String camelCase = "";
		String [] arr = str.split("_");
		List<String> list = new ArrayList<String>();

		//将数组中非空字符串添加至list
		for(String a : arr){
			if(a.length() > 0){
				list.add(a);
			}
		}

		for(int i=0;i<list.size();i++){
			if(i>0){    //后面单词首字母大写
				char c = list.get(i).charAt(0);
				String s = String.valueOf(c).toUpperCase() + list.get(i).substring(1).toLowerCase();
				camelCase+=s;
			}else{  //首个单词小写
				camelCase+=list.get(i).toLowerCase();
			}
		}
		return camelCase;
	}
}
