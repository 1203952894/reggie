package love.mcdd.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import love.mcdd.reggie.common.CustomException;
import love.mcdd.reggie.dto.SetmealDto;
import love.mcdd.reggie.entity.Setmeal;
import love.mcdd.reggie.entity.SetmealDish;
import love.mcdd.reggie.mapper.SetmealMapper;
import love.mcdd.reggie.service.SetmealDishService;
import love.mcdd.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:Cola
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>implements SetmealService {

    @Autowired
    private SetmealDishService  setmealDishService;


    /**
     * 新增套餐 同时保持套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐的基本信息 操作 setmeal 执行 insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        // 保存套餐和菜品的关联信息 操作 setmeal_dish 执行 insert 操作
        setmealDishService.saveBatch(setmealDishes);

    }

    /**
     * 删除套餐 同时删除套餐和菜品
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {

        // 判断当前套餐状态是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);

        if(count >0){
            // 如果不能删除抛出业务异常
            throw new CustomException("套餐正在售卖中不能删除");
        }
        // 如果可以删除则删除套餐表中的数据    setmeal
        this.removeByIds(ids);

        // 删除关系表中的数据 setmeal_dish

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(lambdaQueryWrapper);


    }
}
