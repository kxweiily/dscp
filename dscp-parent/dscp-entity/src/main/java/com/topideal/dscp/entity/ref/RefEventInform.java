package com.topideal.dscp.entity.ref;

import com.baomidou.mybatisplus.annotation.TableName;
import com.topideal.dscp.entity.common.BaseEntity;
import lombok.Data;

/**
 * 用户-事件-模版关系表
 *
 * @Author: yucaixing
 * @Date: 2022/8/1
 */
@Data
@TableName(value = "ref_event_inform")
public class RefEventInform extends BaseEntity {
    private static final long serialVersionUID = 7173660568452612231L;

    /**
     * 事件id
     */
    private String eventId;

    /**
     * 模版id
     */
    private String informId;

}