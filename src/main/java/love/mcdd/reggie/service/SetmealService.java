package love.mcdd.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import love.mcdd.reggie.dto.SetmealDto;
import love.mcdd.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐 同时保持套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐 同时删除套餐和菜品
     */
    public void removeWithDish(List<Long> ids);
}
