package com.lms.demo.repository;

import com.lms.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    // 🔑 Login & identification
    Optional<Employee> findByEmail(String email);

    // 👥 Get employees under a manager
    List<Employee> findByManager(Employee manager);

}
