package love.mcdd.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import love.mcdd.reggie.entity.User;
import love.mcdd.reggie.mapper.UserMapper;
import love.mcdd.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author:Cola
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
