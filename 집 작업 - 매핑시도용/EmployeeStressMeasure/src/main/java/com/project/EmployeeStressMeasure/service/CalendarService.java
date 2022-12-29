package com.project.EmployeeStressMeasure.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CalendarService {
	
	public List<Map<String, Object>> getEventList() {
		Map<String, Object> event = new HashMap<String, Object>();
		List<Map<String, Object>> eventList = new ArrayList<Map<String, Object>>();
		event.put("start", LocalDate.now());
		event.put("title", "test");
		event.put("end", LocalDate.now());
		eventList.add(event);
		event = new HashMap<String, Object>();
		event.put("start", LocalDate.now().plusDays(3));	// 3일 추가
		event.put("title", "test2");
		event.put("end", LocalDate.now().plusDays(6));	// 6일 추가
		eventList.add(event);
		return eventList;
	}
}
