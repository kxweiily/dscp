package com.topideal.dscp.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.response.base.BaseUserRespDto;
import com.topideal.dscp.dto.request.base.BaseUserReqDto;
import com.topideal.dscp.entity.base.BaseUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Mapper - 用户
 *
 * @Author: kongxj
 * @Date: 2022/7/8 11:37
 */
public interface BaseUserMapper extends BaseMapper<BaseUser> {


    /**
     * 分页查询用户
     *
     * @param page           分页信息
     * @param baseUserReqDto 查询封装类
     * @return
     */
    Page<BaseUserRespDto> findPage(Page<?> page, @Param("query") BaseUserReqDto baseUserReqDto);

    /**
     * 通过用户账户(手机/邮箱)查询
     *
     * @param account 用户账户(手机/邮箱)
     * @return
     */
    @Select("select * from base_user where deleted = 0 and is_cancelled = 0 and (tel = #{account} or email = #{account}) limit 1")
    BaseUser findByAccount(@Param("account") String account);

    /**
     * 分页查询员工
     *
     * @param page           分页信息
     * @param baseUserReqDto 查询封装类
     * @return
     */
    Page<BaseUserRespDto> loadPageStaff(Page<?> page, @Param("query") BaseUserReqDto baseUserReqDto);

    /**
     * 解除绑定员工
     *
     * @param userId
     */
    @Update("update base_user set enterprise_id= '', binding_time = NULL, invite_time = NULL, user_type = 'basic'  where id = #{userId}")
    void removeBinding(@Param("userId") String userId);


}
