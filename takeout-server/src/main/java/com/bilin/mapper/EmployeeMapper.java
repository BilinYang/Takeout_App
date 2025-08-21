package com.bilin.mapper;

import com.bilin.result.PageResult;
import com.github.pagehelper.Page;
import com.bilin.annotation.AutoFill;
import com.bilin.dto.EmployeePageQueryDTO;
import com.bilin.entity.Employee;
import com.bilin.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @AutoFill(OperationType.INSERT)
    @Insert("insert into employee values (null, #{name}, #{username}, #{password}, #{phone}, #{sex}, " +
            "#{idNumber}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Employee employee);

    Page<Employee> page(String name);

    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    Employee getById(Long id);
}
