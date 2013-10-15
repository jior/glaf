package com.glaf.mail.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.mail.domain.*;
import com.glaf.mail.query.*;

@Component
public interface MailDataFileMapper {

	void deleteMailDataFiles(MailDataFileQuery query);

	void deleteMailDataFileById(String id);

	MailDataFile getMailDataFileById(String id);

	int getMailDataFileCount(Map<String, Object> parameter);

	int getMailDataFileCountByQueryCriteria(MailDataFileQuery query);

	List<MailDataFile> getMailDataFiles(Map<String, Object> parameter);

	List<Map<String, Object>> getMailDataFileMapList(Map<String, Object> parameter);

	List<MailDataFile> getMailDataFilesByQueryCriteria(MailDataFileQuery query);

	void insertMailDataFile(MailDataFile model);
	
	void insertMailDataFile_postgres(MailDataFile model);

	void updateMailDataFile(MailDataFile model);

}
