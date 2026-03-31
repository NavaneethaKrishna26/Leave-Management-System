package com.lms.demo.repository;

import com.lms.demo.entity.Employee;
import com.lms.demo.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Long> {
    // 📄 Get leaves of a specific employee
    List<Leave> findByEmployee(Employee employee);

    // 👥 Get leaves of team (multiple employees)
    List<Leave> findByEmployeeIn(List<Employee> employees);
}
