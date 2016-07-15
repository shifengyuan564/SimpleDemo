package com.sfy.common;

import java.io.Serializable;
import java.util.List;


/**
 * Mybatis 基本的数据访问接口
 * @author zhaozhiyi
 *
 * @param <T> 执行的类型
 */
public interface DataAccessMapper<T> {

	/**
	 * 保存对象
	 * @param entity
	 * @return 操作影响的行数
	 */
	int save(T entity);
	
	/**
	 * 删除对象
	 * @param id
	 * @return 操作影响的行数
	 */
	int delete(Serializable id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int delete(List ids);
	
	/**
	 * 更新对象
	 * @param entity
	 * @return 操作影响的行数
	 */
	int update(T entity);
	
	/**
	 * 通过主键查询对象
	 * @param id
	 * @return 
	 */
	T get(Serializable id);
	
	/**
	 * 根据条件查询
	 * @param entity
	 * @return
	 */
	List<T> query(T entity);
	
	/**
	 * 查询记录总数
	 * @param entity
	 * @return
	 */
	int count(T entity);
	
	/**
	 * 查询记录总数
	 * @param page
	 * @return
	 */
	int count(Page<T> page);
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	List<T> queryForPage(Page<T> page);

	List<T> queryForPageByMultiOrgAndDistribute(Page<T> page);

	int countByMultiOrgAndDistribute(Page<T> page);

}
