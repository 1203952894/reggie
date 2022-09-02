package love.mcdd.reggie.dto;


import lombok.Data;
import love.mcdd.reggie.entity.Setmeal;
import love.mcdd.reggie.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
