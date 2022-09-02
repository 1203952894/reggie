package love.mcdd.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import love.mcdd.reggie.entity.Employee;
import love.mcdd.reggie.mapper.EmployeeMapper;
import love.mcdd.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author:Cola
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
