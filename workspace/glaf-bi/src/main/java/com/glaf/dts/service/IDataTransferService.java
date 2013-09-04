package com.glaf.dts.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.dts.domain.*;
import com.glaf.dts.query.*;

@Transactional(readOnly = true)
public interface IDataTransferService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> ids);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DataTransfer getDataTransfer(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataTransferCountByQueryCriteria(DataTransferQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DataTransfer> getDataTransfersByQueryCriteria(int start, int pageSize,
			DataTransferQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataTransfer> list(DataTransferQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DataTransfer dataTransfer);

}
