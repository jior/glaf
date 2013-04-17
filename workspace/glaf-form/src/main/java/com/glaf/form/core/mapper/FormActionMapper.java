package com.glaf.form.core.mapper;

import java.util.*;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormAction;
import com.glaf.form.core.query.*;

@Component
public interface FormActionMapper {

	void deleteFormActions(FormActionQuery query);

	void deleteFormActionById(String id);

	FormAction getFormActionById(String id);

	int getFormActionCount(FormActionQuery query);

	List<FormAction> getFormActions(FormActionQuery query);

	void insertFormAction(FormAction model);

	void updateFormAction(FormAction model);

}
