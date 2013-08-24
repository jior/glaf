package com.glaf.cms.fullcalendar.mapper;

import java.util.*;
import org.springframework.stereotype.Component;
import com.glaf.cms.fullcalendar.model.*;
import com.glaf.cms.fullcalendar.query.*;

@Component
public interface FullCalendarMapper {

	void deleteFullCalendars(FullCalendarQuery query);

	void deleteFullCalendarById(Long id);

	FullCalendar getFullCalendarById(Long id);

	int getFullCalendarCount(FullCalendarQuery query);

	List<FullCalendar> getFullCalendars(FullCalendarQuery query);

	void insertFullCalendar(FullCalendar model);

	void updateFullCalendar(FullCalendar model);

}
