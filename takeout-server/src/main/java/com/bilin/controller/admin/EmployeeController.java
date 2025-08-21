package com.bilin.controller.admin;

import com.bilin.constant.JwtClaimsConstant;
import com.bilin.dto.EmployeeDTO;
import com.bilin.dto.EmployeeLoginDTO;
import com.bilin.dto.EmployeePageQueryDTO;
import com.bilin.entity.Employee;
import com.bilin.properties.JwtProperties;
import com.bilin.result.PageResult;
import com.bilin.result.Result;
import com.bilin.service.EmployeeService;
import com.bilin.utils.JwtUtil;
import com.bilin.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags= "Employee API")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation(value = "Employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("Employee Login：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }


    @PostMapping("/logout")
    @ApiOperation("Employee logout")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation("Add employee")
    public Result addEmp(@RequestBody EmployeeDTO dto) {
        log.info("Add new employee: {}", dto);
        employeeService.addEmp(dto);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("Search employee(s)")
    public Result<PageResult> page(EmployeePageQueryDTO dto) {
        log.info("Search employees by page: {}", dto);
        PageResult pageResult = employeeService.page(dto);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("Enable or Disable Employee Status")
    public Result enableOrDisable(@PathVariable Integer status, Long id) {
        log.info("Enable/Disable employee status: {}, id: {}", status, id);
        employeeService.enableOrDisable(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get Employee by ID")
    public Result getById(@PathVariable Long id) {
        log.info("Get employee by id: {}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping()
    @ApiOperation("Edit Employee")
    public Result editEmp(@RequestBody EmployeeDTO dto) {
        log.info("Edit employee: {}", dto);
        employeeService.update(dto);
        return Result.success();
    }
}