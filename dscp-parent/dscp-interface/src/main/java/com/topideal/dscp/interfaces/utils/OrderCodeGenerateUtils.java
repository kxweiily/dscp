package com.topideal.dscp.interfaces.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.topideal.dscp.interfaces.redis.RedisStringUtils;

/**
 * 获取订单编码工具类
 */
@Component
public class OrderCodeGenerateUtils {

	@Autowired
    private RedisStringUtils redisStringUtils;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    /**
     * 生成订单编码
     * 订单前缀+八位日期+四位流水0001(如果流水超过4位数，则按实际位数)
     * @param code 订单前缀
     * @return
     */
    public String generateCode(String code) {
        //订单前缀+日期
        String prefix = code+sdf.format(new Date());
        String index = null;
        int size = 4;
        //如果日期前缀未过期，则序号自增
        if (redisStringUtils.hasKey(prefix)) {
        	index = String.valueOf(redisStringUtils.increment(prefix, 1));
        } else {//否则，将日期作为Key，1作为Value重置，并设置第二天0点过期
        	redisStringUtils.set(prefix, "1");
        	redisStringUtils.expireAt(prefix, getMidnightDate());
            index = "1";
        }
        if(index.length() > size) {//长度超过4位数，按当前位数
        	size = index.length();
        }
        String generateCode=new StringBuilder(prefix).append(StringUtils.leftPad(index,size,"0")).toString();
        return generateCode;
    }


    // 获取第二天00:00的时间
    private static Date getMidnightDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
