package com.topideal.dscp.cms.config.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.dto.response.sys.SysUserRespDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * spring security 登陆后数据封装类 - 普通用户
 *
 * @Author: kongxj
 * @Date: 2020/6/15 19:46
 */

public class DscpUser implements UserDetails {

    private static final long serialVersionUID = -6224604995856019561L;

    /**
     * 账户类型   0:系统用户   1:普通用户
     */
    private final Integer accountType;

    /**
     * 用户id
     */
    private final String userId;

    /**
     * 普通用户绑定商家id
     */
    private final String enterpriseId;

    /**
     * 用户权限
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * 本次请求 是否有数据权限
     */
    private Boolean hasDataAuthority = Boolean.FALSE;

    /**
     * 是否可用
     */
    private boolean enabled;


    public DscpUser(Integer accountType, String userId, String enterpriseId, Collection<? extends GrantedAuthority> authorities) {
        this(accountType, userId, enterpriseId, authorities, true);
    }

    public DscpUser(Integer accountType, String userId, String enterpriseId, Collection<? extends GrantedAuthority> authorities, boolean enabled) {

        if (StringUtils.isBlank(userId)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.accountType = accountType;
        this.userId = userId;
        this.enterpriseId = enterpriseId;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    // 返回空
    @Override
    public String getUsername() {
        return "";
    }

    // 返回空
    @Override
    public String getPassword() {
        return "";
    }


    public Integer getAccountType() {
        return accountType;
    }

    public String getUserId() {
        return userId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public Boolean getHasDataAuthority() {
        return hasDataAuthority;
    }

    public void setHasDataAuthority(Boolean hasDataAuthority) {
        this.hasDataAuthority = hasDataAuthority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 权限数据转移到RbacService处理
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { // 帐户是否过期
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { // 帐户是否被冻结
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { // 帐户密码是否过期，一般有的密码要求性高的系统会使用到，比较每隔一段时间就要求用户重置密码
        return true;
    }

    @Override
    public boolean isEnabled() { // 帐号是否可用
        return enabled;
    }


    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing
     * the same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof DscpUser) {
            return userId.equals(((DscpUser) rhs).userId);
        }
        return false;
    }


    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return userId.hashCode();
    }

}
