package love.mcdd.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import love.mcdd.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
