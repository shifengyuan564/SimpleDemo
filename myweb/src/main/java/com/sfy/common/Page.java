package com.sfy.common;

import java.io.Serializable;
import java.util.List;

/**分页*/
 
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 211597880115601895L;

	private Object param;   // 查询参数
	private List<T> data;   // 分页数据
	private Integer size;   // 分页大小
	private Integer skip;   // 偏移量
	private Integer total;  // 记录总数

	public Object getParam() {
		return param;
	}
	public void setParam(Object param) {
		this.param = param;
	}

	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getSkip() {
		return skip;
	}
	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

}
