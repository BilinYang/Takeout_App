package com.bilin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.bilin.constant.MessageConstant;
import com.bilin.constant.PasswordConstant;
import com.bilin.constant.StatusConstant;
import com.bilin.context.BaseContext;
import com.bilin.dto.EmployeeDTO;
import com.bilin.dto.EmployeeLoginDTO;
import com.bilin.dto.EmployeePageQueryDTO;
import com.bilin.entity.Employee;
import com.bilin.exception.AccountLockedException;
import com.bilin.exception.AccountNotFoundException;
import com.bilin.exception.PasswordErrorException;
import com.bilin.mapper.EmployeeMapper;
import com.bilin.result.PageResult;
import com.bilin.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        Employee employee = employeeMapper.getByUsername(username);

        if (employee == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // Encode with MD5
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return employee;
    }

    @Override
    public void addEmp(EmployeeDTO dto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);

        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        // Get and parse token to get the person who created/updated the employee from current thread
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

    @Override
    public PageResult page(EmployeePageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());

        Page<Employee> page = employeeMapper.page(dto.getName());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void enableOrDisable(Integer status, Long id){
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        return employeeMapper.getById(id);
    }

    @Override
    public void update(EmployeeDTO dto){
       Employee employee = new Employee();
       BeanUtils.copyProperties(dto, employee);
//       employee.setUpdateTime(LocalDateTime.now());
//       employee.setUpdateUser(BaseContext.getCurrentId());
       employeeMapper.update(employee);
    }
}
