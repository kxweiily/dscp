package com.topideal.dscp.cms.config.mybaits;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.topideal.dscp.cms.config.security.DscpUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Mybatis 字段预处理类
 *
 * @Author: kongxj
 * @Date: 2020/6/12 17:40
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增预处理字段
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        // 创建时间
        if (metaObject.hasGetter("createTime") ) {
            setFieldValByName("createTime", now, metaObject);
        }
        // 创建人
        if (metaObject.hasGetter("creator")) {
            setFieldValByName("creator", getUserId(), metaObject);
        }
        // 编辑时间
        if (metaObject.hasGetter("editTime")) {
            setFieldValByName("editTime", now, metaObject);
        }
        // 编辑人
        if (metaObject.hasGetter("editor")) {
            setFieldValByName("editor", getUserId(), metaObject);
        }
        // 是否删除
        if (metaObject.hasGetter("deleted")) {
            setFieldValByName("deleted", Boolean.FALSE, metaObject);
        }
    }

    /**
     * 更新预处理字段
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        // 编辑时间
        if (metaObject.hasGetter("editTime")) {
            setFieldValByName("editTime", now, metaObject);
        }
        // 编辑人
        if (metaObject.hasGetter("editor")) {
            setFieldValByName("editor", getUserId(), metaObject);
        }
    }

    /**
     * 获取上下文中当前操作人id
     * @return
     */
    private String getUserId() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
            Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (object instanceof DscpUser) {
                return ((DscpUser)object).getUserId();
            }
        }
        return null;
    }
}
