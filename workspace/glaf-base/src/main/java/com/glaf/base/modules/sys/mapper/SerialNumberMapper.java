package com.glaf.base.modules.sys.mapper;

import java.util.*;
import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.SerialNumber;
import com.glaf.base.modules.sys.query.SerialNumberQuery;

@Component
public interface SerialNumberMapper {

	void deleteSerialNumbers(SerialNumberQuery query);

	void deleteSerialNumberById(Long id);

	SerialNumber getSerialNumberById(Long id);

	int getSerialNumberCount(SerialNumberQuery query);

	List<SerialNumber> getSerialNumbers(SerialNumberQuery query);

	void insertSerialNumber(SerialNumber model);

	void updateSerialNumber(SerialNumber model);

}
