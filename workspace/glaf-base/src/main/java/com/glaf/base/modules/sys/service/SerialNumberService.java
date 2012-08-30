package com.glaf.base.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.glaf.base.modules.sys.model.SerialNumber;

public interface SerialNumberService {

	List<SerialNumber> getImportSupplierSerialNumber(Map params);

	String getSerialNumber(Map params);

	// ͨ��ģ��NO��category��area������SerialNumber�ж�Ӧ��moduleNo���Ӷ���ȡSerialNumber����
	List<SerialNumber> getSupplierSerialNumber(Map params);

	boolean update(SerialNumber bean);

}