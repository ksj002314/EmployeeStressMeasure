package com.project.EmployeeStressMeasure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.EmployeeStressMeasure.domain.ECalendar;

public interface CalendarRepository extends JpaRepository<ECalendar, Long> {

}
