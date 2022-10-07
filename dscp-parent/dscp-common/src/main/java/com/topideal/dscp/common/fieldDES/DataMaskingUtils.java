package com.topideal.dscp.common.fieldDES;

import com.topideal.dscp.common.exception.BizException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *  数据脱敏 处理工具类
 *
 * @Author: kongxj
 * @Date: 2022年8月2日14:16:40
 */
public class DataMaskingUtils {

    /**
     *  脱敏处理
     *
     * @param obj          处理数据
     * @return
     */
    public static void execute(Object obj) {

        if (Objects.isNull(obj)) return;
        try {
            // 获取class 所有声明的字段
            List<Field> list = Arrays.asList(obj.getClass().getDeclaredFields());
            if (CollectionUtils.isNotEmpty(list)) {
                for (Field field : list) {
                    // 字段是否使用了 @FieldDES 注解
                    if (field.isAnnotationPresent(FieldDES.class)) {
                        FieldDES annotation = field.getAnnotation(FieldDES.class);
                        // 是否脱敏
                        if (annotation.isDM()) {
                            // 脱敏类型
                            DataMaskingType dmType = annotation.dmType();
                            field.setAccessible(true);
                            Object value = field.get(obj);
                            // 不为空且是String类
                            if (value != null && value instanceof String) {
                                String strValue = (String)value;
                                if (StringUtils.isNotBlank(strValue)) {

                                    // 脱敏处理方法
                                    strValue = handleDate(strValue, dmType);
                                    field.set(obj, strValue);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    /**
     *  脱敏处理 （列表数据）
     *
     * @param objs          处理列表数据
     * @return
     */
    public static void executeList(List<?> objs) {
        if (CollectionUtils.isEmpty(objs)) return;
        for (Object obj : objs) {
            execute(obj);
        }
    }

    /**
     * @脱敏核心逻辑处理方法
     *
     * @param strValue   需要脱敏的值
     * @param maskingType 脱敏类型
     * @return
     */
    public static String handleDate(String strValue, DataMaskingType maskingType) {
        char[] array = strValue.toCharArray();

        if (DataMaskingType.none.equals(maskingType)) { // 无 不处理
            return strValue;
        }

        if (DataMaskingType.password.equals(maskingType)) { // 密码 直接置空
            return "";

        } else if (DataMaskingType.identityCard.equals(maskingType)) { // 身份证 替换中间出生日期8位数
            for (int i = 0; i < array.length; i++) {
                if (i > 5 && i < 14) {
                    array[i] = '*';
                }
            }

        } else if (DataMaskingType.telephone.equals(maskingType)) { // 手机号码 展示前三位与后四位，替换中间四位
            for (int i = 0; i < array.length; i++) {
                if (i > 2 && i < 7) {
                    array[i] = '*';
                }
            }

        } else if (DataMaskingType.email.equals(maskingType)) { // 邮箱 展示前后各一位
            for (int i = 0; i < array.length; i++) {
                if (i != 0 && i!= array.length - 1) {
                    array[i] = '*';
                }
            }

        } else if (DataMaskingType.name.equals(maskingType)) { // 姓名
            // 姓名为2字的，替换最后一个字
            // 姓名为3字的，替换中间一个字
            // 姓名多于3字的，展示第一个与最后一个字，中间全部用*替换处理
            for (int i = 0; i < array.length; i++) {
                if (i == 1 || (i > 1 && i != array.length - 1)) {
                    array[i] = '*';
                }
            }

        } else if (DataMaskingType.address.equals(maskingType)) { // 地址  展示前后各两位
            for (int i = 0; i < array.length; i++) {
                if (i > 1 && i < array.length - 2) {
                    array[i] = '*';
                }
            }

        } else if (DataMaskingType.picture.equals(maskingType)) { // 图片 替换成屏蔽图url
            return "/resources/images/no_authorization.png";

        }

        strValue = String.valueOf(array);
        return strValue;
    }
}
