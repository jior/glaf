package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.glaf.base.modules.sys.model.SerialNumber;

public interface SerialNumberService {

	List<SerialNumber> getImportSupplierSerialNumber(Map params);

	String getSerialNumber(Map params);

	// 通过模块NO、category、area来查找SerialNumber中对应的moduleNo，从而获取SerialNumber对象
	List<SerialNumber> getSupplierSerialNumber(Map params);

	boolean update(SerialNumber bean);

}