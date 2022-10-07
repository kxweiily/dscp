package com.topideal.dscp.dto.common;

import java.io.Serializable;

import lombok.Data;

/**
 *  用于展示下拉框
 */
@Data
public class SelectBeanDto implements Serializable {

	private String label;

	private String value;

	public SelectBeanDto() {

	}

	public SelectBeanDto(String label, String value) {
		this.label = label;
		this.value = value;
	}

}
