package com.topideal.dscp.common.jwt;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * JWT登录 token工具类
 * <p>
 * lizhenni  2020-06-16 11:00
 */
@Slf4j
public class JWTUtils {

    /**
     * jwt token 前缀
     */
    public static final String TOKEN_PREFIX = "Authentication";

    /**
     * jwt user id
     */
    public static final String USER_ID = "userId";

    /**
     * jwt 账户类型   0:系统用户   1:普通用户
     */
    public static final String ACCOUNT_TYPE = "accountType";

    /**
     * jwt 绑定商家id
     */
    public static final String ENTERPRISE_ID = "enterpriseId";


    // 加密密文，私钥
    public static final String JWT_SECRET = "bbdqweasasdqweasasdqweasasdqweasasdqweasasdqweasasdqweasasdqweas";

    // 获取加密key
    public static SecretKey generalKey() {
        // 本地的密码解码
        byte[] encodedKey = Base64.getDecoder().decode(JWT_SECRET);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
        return key;
    }

    /**
     * 创建token
     *
     * @param json     必填，json格式的字符串
     * @param expTime  非必填，token有效时间
     * @return
     */
    public static String createJWT(JSONObject json, Long expTime) {
        // 指定header那部分签名的时候使用的签名算法，jjwt已经将这部分内容封装好了，只有{"alg":"HS256"}
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // jti用户id，例如：20da39f8-b74e-4a9b-9a0f-a39f1f73fe64
        String jwtId = UUID.randomUUID().toString();
        // 生成JWT的时间
        long nowTime = System.currentTimeMillis();
        Date issuedAt = new Date(nowTime);
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露，是你服务端的私钥，在任何场景都不应该流露出去，一旦客户端得知这个secret，那就意味着客户端是可以自我签发jwt的
        SecretKey key = generalKey();
        // 为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder() // 表示new一个JwtBuilder，设置jwt的body
                .setId(jwtId) // jti(JWT ID)：jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setIssuedAt(issuedAt) // iat(issuedAt)：jwt的签发时间
                .setIssuer("DSCP") // iss(issuer)：jwt签发者
                .setSubject(json.toJSONString()) // sub(subject)：jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
                .signWith(key, signatureAlgorithm); // 设置签名，使用的是签名算法和签名使用的秘钥
        // 设置过期时间
        if (expTime != null && expTime >= 0) {
            long exp = nowTime + expTime;
            builder.setExpiration(new Date(exp));
        }

        log.info("登录创建token，存放在token中的数据{}", json.toJSONString());
        return builder.compact();
    }

    /**
     * 解密jwt  返回保存在token中用户信息的JSONObject格式
     *
     * @param jwtString
     * @return 解析成JSONObject
     * @throws
     */
    public static JSONObject parseJWTJSON(String jwtString) {
        SecretKey key = generalKey(); // 签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parserBuilder() // 得到DefaultJwtParser
                .setSigningKey(key) // 设置签名的秘钥
                .build()
                .parseClaimsJws(jwtString).getBody(); // 设置需要解析的jwt
        JSONObject jsonObject = JSONObject.parseObject(claims.getSubject());

        return jsonObject;
    }

    /**
     * 获得 token中的  userId
     *
     * @param jwtString
     * @return
     */
    public static String getUserId(String jwtString) {
        return parseJWTJSON(jwtString).getString(USER_ID);
    }


    /**
     * 获得token中的  账户类型   0:系统用户   1:普通用户
     *
     * @param jwtString
     * @return
     */
    public static String getAccountType(String jwtString) {
        return parseJWTJSON(jwtString).getString(ACCOUNT_TYPE);
    }

    /**
     * 获得token中的  店主 姓名
     *
     * @param jwtString
     * @return
     */
    public static String getEnterpriseId(String jwtString) {
        return parseJWTJSON(jwtString).getString(ENTERPRISE_ID);
    }

}
