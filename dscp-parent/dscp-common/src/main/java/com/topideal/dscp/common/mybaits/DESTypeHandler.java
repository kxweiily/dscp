package com.topideal.dscp.common.mybaits;

import com.topideal.dscp.common.fieldDES.RSADESUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 废弃 加解密处理器
 * mybatis TypeHandler方式处理加解密 但不想用这种方式。不太灵活
 * 自定义sql 需要xml注解比较麻烦
 * @Author: kongxj
 * @Date: 2022/7/11 14:09
 */
@Slf4j
//@Component
public class DESTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            if (StringUtils.isBlank((String) parameter)) {
                return;
            }
            // 加密操作
            String encrypt = RSADESUtils.encrypt((String)parameter);
            preparedStatement.setString(i, encrypt);
        } catch (Exception e) {
            log.error("DESTypeHandler加密异常：" + e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        if (StringUtils.isNotBlank(value)) {
            try {
                return RSADESUtils.decrypt(value);
            } catch (Exception e) {
                log.error("DESTypeHandler解密异常：" + e);
            }
        }
        return value;
    }

    @Override
    public Object getNullableResult(ResultSet rs, int i) throws SQLException {
        String value = rs.getString(i);
        if (StringUtils.isNotBlank(value)) {
            try {
                return RSADESUtils.decrypt(value);
            } catch (Exception e) {
                log.error("DESTypeHandler解密异常：" + e);
            }
        }
        return value;
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int i) throws SQLException {
        String value = cs.getString(i);
        if (StringUtils.isNotBlank(value)) {
            try {
                return RSADESUtils.decrypt(value);
            } catch (Exception e) {
                log.error("DESTypeHandler解密异常：" + e);
            }
        }
        return value;
    }
}
