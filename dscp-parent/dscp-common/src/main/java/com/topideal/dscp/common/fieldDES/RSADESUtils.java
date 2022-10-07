package com.topideal.dscp.common.fieldDES;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * RSA 加解密 工具类
 *
 * 使用非对称加密算法RSA算法组件
 * 非对称算法一般是用来传送对称加密算法的密钥来使用的，相对于DH算法，RSA算法只需要一方构造密钥，不需要
 * 大费周章的构造各自本地的密钥对了。DH算法只能算法非对称算法的底层实现。而RSA算法算法实现起来较为简单
 *
 * @Author: kongxj
 * @Date: 2022/7/8 17:55
 */
public class RSADESUtils {

    public static final String logalrithm = "AES/CBC/PKCS5Padding";
    public static final String bm = "utf-8";
    private static byte[] keyValue = "`&^%T_&*o(+^p&@;".getBytes();
    private static byte[] iv = "`&^%T_&*o(+^p&@;".getBytes();

    private static Key keySpec;
    private static IvParameterSpec ivSpec;

    static{
        keySpec = new SecretKeySpec(keyValue, "AES");
        ivSpec = new IvParameterSpec(iv);
    }

    /**
     * @Title: encrypt
     * @Description: 加密，使用指定数据源生成密钥，使用用户数据作为算法参数进行AES加密
     * @return String    返回类型
     * @param msg 加密的数据
     * @return
     * @date 2015-9-23 上午9:09:20
     * @throws
     */
    public static String encrypt(String msg) {
        if(StringUtils.isEmpty(msg)){
            return msg;
        }
        try {
            Cipher ecipher = Cipher.getInstance(logalrithm);
            ecipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryptedData = ecipher.doFinal(msg.getBytes(bm));
            return asHex(encryptedData);
        } catch (Exception e){
            System.out.println("加密的数据出错啦");
        }
        return msg;
    }


    /**
     * @Title: decrypt
     * @Description: 解密，对生成的16进制的字符串进行解密
     * @return String    返回类型
     * @param value
     * @return
     * @date 2015-9-23 上午9:10:01
     * @throws
     */
    public static String decrypt(String value) {
        if(StringUtils.isBlank(value)){
            return value;
        }
        try {
            Cipher ecipher = Cipher.getInstance(logalrithm);
            ecipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(ecipher.doFinal(asBin(value)),"utf-8");
        } catch (Exception e) {
            System.out.println("解密错误："+value);
        }
        return value;
    }


    /**
     * @Title: asHex
     * @Description: 将字节数组转换成16进制字符串
     * @return String    返回类型
     * @param buf
     * @return
     * @date 2015-9-23 上午9:10:25
     * @throws
     */
    private static String asHex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10){
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }


    /**
     * @Title: asBin
     * @Description: 将16进制字符串转换成字节数组
     * @return byte[]    返回类型
     * @param src
     * @return
     * @date 2015-9-23 上午9:10:52
     * @throws
     */
    private static byte[] asBin(String src) {
        if (src.length() < 1){
            return null;
        }
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    /**
     * 加密方法
     * 核心处理：根据 实体class 中 变量上 @FieldDES 注解 对该变量执行加解密
     * @param obj 实体
     * @return
     */
    public static void encryptClass(Object obj) {
        if (Objects.isNull(obj)) return;
        try {
            // 获取class 所有声明的字段
            List<Field> list = Arrays.asList(obj.getClass().getDeclaredFields());
            if (CollectionUtils.isNotEmpty(list)) {
                for (Field field : list) {
                    // 字段是否使用了 @FieldDES 注解
                    if (field.isAnnotationPresent(FieldDES.class)) {
                        FieldDES annotation = field.getAnnotation(FieldDES.class);
                        if (annotation.isDES()) { // 是否加解密
                            field.setAccessible(true);
                            Object value = field.get(obj);
                            // 不为空且是String类
                            if (value != null && value instanceof String) {
                                String strValue = (String)value;
                                if (StringUtils.isNotBlank(strValue)) {
                                    strValue = encrypt(strValue);
                                    field.set(obj, strValue);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量解密方法
     * @param objs 解密字符串
     * @return
     */
    public static void decryptList(List<?> objs) {
        if (CollectionUtils.isEmpty(objs)) return;
        for (Object obj : objs) {
            decryptClass(obj);
        }
    }

    /**
     * 解密方法
     * 核心处理：根据 实体class 中 变量上 @FieldDES 注解 对该变量执行加解密
     * @param obj 实体
     * @return
     */
    public static void decryptClass(Object obj) {
        try {
            // 获取class 所有声明的字段
            List<Field> list = Arrays.asList(obj.getClass().getDeclaredFields());
            if (CollectionUtils.isNotEmpty(list)) {
                for (Field field : list) {
                    // 字段是否使用了 @FieldDES 注解
                    if (field.isAnnotationPresent(FieldDES.class)) {
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        // 不为空且是String类
                        if (value != null && value instanceof String) {
                            String strValue = (String)value;
                            if (StringUtils.isNotBlank(strValue)) {
                                strValue = decrypt(strValue);
                                field.set(obj, strValue);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
