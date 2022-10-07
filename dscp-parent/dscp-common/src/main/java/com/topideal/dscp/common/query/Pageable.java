package com.topideal.dscp.common.query;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 分页信息
 * 
 *
 * @author sunjian
 * @version 1.0
 */
public final class Pageable implements Serializable {

	private static final long serialVersionUID = -4077992127018297606L;

	/** 最小页码 */
	private static final int MIN_PAGE = 1;
	
	/** 最小每页记录数 */
	private static final int MIN_ROWS = 1;
	
	/** 最大每页记录数 */
	private static final int MAX_ROWS = Integer.MAX_VALUE;
	
	/** 默认页码 */
	private static final int DEFAULT_PAGE = MIN_PAGE;

	/** 默认每页记录数 */
	private static final int DEFAULT_ROWS = 50;

	/** 页码 */
	private int page = DEFAULT_PAGE;

	/** 每页记录数 */
	private int rows = DEFAULT_ROWS;

	public Pageable() {
	}

	/**
	 * @param page 页码
	 * @param rows 每页记录数
	 */
	public Pageable(Integer page, Integer rows) {
		if (page != null && page >= MIN_PAGE) {
			this.page = page;
		}
		if (rows != null && rows >= MIN_ROWS && rows <= MAX_ROWS) {
			this.rows = rows;
		}
	}

	/**
	 * @return 页码
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page 页码
	 */
	public void setPage(int page) {
		if (page < MIN_PAGE) {
			page = DEFAULT_PAGE;
		}
		this.page = page;
	}

	/**
	 * @return 每页记录数
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows 每页记录数
	 */
	public void setRows(int rows) {
		if (rows < MIN_ROWS || rows > MAX_ROWS) {
			rows = DEFAULT_ROWS;
		}
		this.rows = rows;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Pageable other = (Pageable) obj;
		return new EqualsBuilder().append(getPage(), other.getPage()).append(getRows(), other.getRows()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getPage()).append(getRows()).toHashCode();
	}

}
