package love.mcdd.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import love.mcdd.reggie.dto.DishDto;
import love.mcdd.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    /**
     * 操作 dish  dish_flavor 插入菜品对应的口味数据
     */
    public void saveWithFlavor(DishDto dishDto);

    /**
     * 根据 id 查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id);

    /**
     * 更新菜品信息 同时更新口味信息
     * @param dishDto
     */
    public void updateWithFlavor(DishDto dishDto);

}
