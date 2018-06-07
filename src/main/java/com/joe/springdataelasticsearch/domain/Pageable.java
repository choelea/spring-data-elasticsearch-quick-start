
package com.joe.springdataelasticsearch.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;


public class Pageable implements Serializable {

	private static final long serialVersionUID = -3930180379790344299L;

	/**
	 * 默认页码
	 */
	private static final int DEFAULT_PAGE_NUMBER = 1;

	/**
	 * 默认每页记录数
	 */
	private static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 最大每页记录数
	 */
	private static final int MAX_PAGE_SIZE = 20;

	/**
	 * 页码
	 */
	private int pageNumber = DEFAULT_PAGE_NUMBER;

	/**
	 * 每页记录数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;

	
	/**
	 * 构造方法
	 */
	public Pageable() {
	}

	/**
	 * 构造方法
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 */
	public Pageable(Integer pageNumber, Integer pageSize) {
		if (pageNumber != null && pageNumber >= 1) {
			this.pageNumber = pageNumber;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 *            页码
	 */
	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
		this.pageNumber = pageNumber;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	
	/**
	 * 重写hashCode方法
	 * 
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}