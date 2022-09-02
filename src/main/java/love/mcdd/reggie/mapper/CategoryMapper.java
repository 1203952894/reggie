package love.mcdd.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import love.mcdd.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
