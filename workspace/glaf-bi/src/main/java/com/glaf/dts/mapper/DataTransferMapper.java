package com.glaf.dts.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.dts.domain.*;
import com.glaf.dts.query.*;

@Component
public interface DataTransferMapper {

	void deleteDataTransfers(DataTransferQuery query);

	void deleteDataTransferById(String id);

	DataTransfer getDataTransferById(String id);

	int getDataTransferCount(DataTransferQuery query);

	List<DataTransfer> getDataTransfers(DataTransferQuery query);

	void insertDataTransfer(DataTransfer model);

	void updateDataTransfer(DataTransfer model);

}
