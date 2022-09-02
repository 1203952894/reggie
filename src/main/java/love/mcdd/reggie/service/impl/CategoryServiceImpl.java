package love.mcdd.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import love.mcdd.reggie.common.CustomException;
import love.mcdd.reggie.entity.Category;
import love.mcdd.reggie.entity.Dish;
import love.mcdd.reggie.entity.Setmeal;
import love.mcdd.reggie.mapper.CategoryMapper;
import love.mcdd.reggie.service.CategoryService;
import love.mcdd.reggie.service.DishService;
import love.mcdd.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:Cola
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据 id 删除分类 删除之前需要进行判断
     *
     * @param id
     */
    @Override
    public void remomve(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件 根据分类 id
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);

        int count1 = dishService.count(dishLambdaQueryWrapper);

        // 查询当前分类是否关联菜品 如果已经关联 抛出一个业务异常
        if (count1 > 0) {
            // 已经关联菜品 抛出异常
            throw new CustomException("当前分类下已关联菜品无法删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count();

        // 查询当前分类是否关联套餐 如果已经关联 抛出一个业务异常
        if (count2 > 0) {
            // 已经关联套餐 抛出异常
            throw new CustomException("当前分类下已关联套餐无法删除");

        }

        // 正常删除
        super.removeById(id);

    }
}
