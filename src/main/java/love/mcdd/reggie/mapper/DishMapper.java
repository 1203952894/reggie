package love.mcdd.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import love.mcdd.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
