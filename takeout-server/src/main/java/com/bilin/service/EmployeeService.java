package com.bilin.service;

import com.bilin.dto.EmployeeDTO;
import com.bilin.dto.EmployeeLoginDTO;
import com.bilin.dto.EmployeePageQueryDTO;
import com.bilin.entity.Employee;
import com.bilin.result.PageResult;

public interface EmployeeService {

    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void addEmp(EmployeeDTO dto);

    PageResult page(EmployeePageQueryDTO dto);

    void enableOrDisable(Integer status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO dto);
}
