package com.topideal.dscp.impl.biz;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.topideal.dscp.common.constants.RedisConstants;
import com.topideal.dscp.common.constants.TimeConstants;
import com.topideal.dscp.common.exception.BizException;
import com.topideal.dscp.common.fieldDES.RSADESUtils;
import com.topideal.dscp.common.jwt.JWTUtils;
import com.topideal.dscp.interfaces.redis.RedisHashMapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.topideal.dscp.dto.request.biz.BizGiveFeedbackReqDto;
import com.topideal.dscp.dto.response.biz.BizGiveFeedbackRespDto;
import com.topideal.dscp.entity.biz.BizGiveFeedback;
import com.topideal.dscp.mapper.biz.BizGiveFeedbackMapper;
import com.topideal.dscp.service.biz.BizGiveFeedbackService;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ServiceImpl - 意见反馈
 */
@Service
public class BizGiveFeedbackServiceImpl implements BizGiveFeedbackService {

    @Resource
    private BizGiveFeedbackMapper bizGiveFeedbackMapper;

	@Resource
	private RedisHashMapUtils redisHashMapUtils;
    
    
	@Override
	@Transactional(readOnly = true)
	public BizGiveFeedbackRespDto findById(String id) {
		BizGiveFeedback bizGiveFeedback = bizGiveFeedbackMapper.selectById(id);
		BizGiveFeedbackRespDto bizGiveFeedbackRespDto = new BizGiveFeedbackRespDto();
		if (bizGiveFeedback != null) {
            BeanUtils.copyProperties(bizGiveFeedback, bizGiveFeedbackRespDto);
        }
        return bizGiveFeedbackRespDto;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<BizGiveFeedbackRespDto> findPage(Page<?> page, BizGiveFeedbackReqDto bizGiveFeedbackReqDto) {
		return bizGiveFeedbackMapper.findPage(page, bizGiveFeedbackReqDto);
	}
	
	@Override
	@Transactional
	public void update(BizGiveFeedbackRespDto bizGiveFeedbackRespDto) {
		//更新
		BizGiveFeedback bizGiveFeedback = new BizGiveFeedback();
		BeanUtils.copyProperties(bizGiveFeedbackRespDto, bizGiveFeedback);
		bizGiveFeedbackMapper.updateById(bizGiveFeedback);
	}

	/**
	 * pc端   分页查询
	 *
	 * @param page
	 * @param bizGiveFeedbackReqDto
	 * @return
	 */
	@Override
	public Map<String, Object> pcLoadPage(Page<?> page, BizGiveFeedbackReqDto bizGiveFeedbackReqDto) {
		Map<String,Object> map = bizGiveFeedbackMapper.selectByStatus(bizGiveFeedbackReqDto.getEnterpriseId());

		Page<BizGiveFeedbackRespDto> dtoPage = bizGiveFeedbackMapper.findPage(page, bizGiveFeedbackReqDto);
		map.put("page",dtoPage);
		return map;
	}

	/**
	 * 新增反馈
	 *
	 * @param bizGiveFeedbackReqDto
	 * @return
	 */
	@Override
	public void insert(BizGiveFeedbackReqDto bizGiveFeedbackReqDto) {
		BizGiveFeedback bizGiveFeedback = new BizGiveFeedback();
		BeanUtils.copyProperties(bizGiveFeedbackReqDto,bizGiveFeedback);
		bizGiveFeedbackMapper.insert(bizGiveFeedback);
	}

	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@Override
	public BizGiveFeedbackRespDto selectById(String id) {
		BizGiveFeedback bizGiveFeedback = bizGiveFeedbackMapper.selectById(id);
		if (bizGiveFeedback != null) {
			BizGiveFeedbackRespDto bizGiveFeedbackRespDto = new BizGiveFeedbackRespDto();
			BeanUtils.copyProperties(bizGiveFeedback, bizGiveFeedbackRespDto);
			return bizGiveFeedbackRespDto;
		}
		return null;
	}


	/**
	 * 检查订单号是否存在
	 *
	 * @param id
	 * @return
	 */
	@Override
	public void check(String id,String enterpriseId) {

		//一般贸易进出口订单号
		ArrayList<String> importExportList = bizGiveFeedbackMapper.selectImportExport(enterpriseId);

		//保税入区订单
		ArrayList<String> entryList = bizGiveFeedbackMapper.selectEntry(enterpriseId);
		//保税出区订单
		ArrayList<String> exitList = bizGiveFeedbackMapper.selectExit(enterpriseId);

		//调拨出入仓单
		ArrayList<String> allotList = bizGiveFeedbackMapper.selectAllot(enterpriseId);

		//合并所有订单号
		List<String> collect = Stream.of(importExportList, entryList, exitList, allotList).flatMap(Collection::stream).collect(Collectors.toList());

		//是否存在该订单号
		if (!collect.stream().filter(item->item.equals(id)).findAny().isPresent()) {
			throw new BizException("订单号不存在");
		}

	}

	/**
	 * 修改状态为关闭
	 *
	 * @param status
	 * @param id
	 * @return
	 */
	@Override
	public void status(String status, String id) {
		BizGiveFeedback bizGiveFeedback = new BizGiveFeedback();
		bizGiveFeedback.setStatus(status);
		bizGiveFeedback.setId(id);
		bizGiveFeedbackMapper.updateById(bizGiveFeedback);
	}
}
