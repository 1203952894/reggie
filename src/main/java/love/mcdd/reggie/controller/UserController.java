package love.mcdd.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import love.mcdd.reggie.common.R;
import love.mcdd.reggie.entity.User;
import love.mcdd.reggie.service.UserService;
import love.mcdd.reggie.utils.SMSUtils;
import love.mcdd.reggie.utils.ValidateCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author:Cola
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg( @RequestBody User user,HttpSession httpSession){

        // 获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            // 生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("本次登录验证码为：code={}",code);
            // 调用 短信服务 API 进行发送短信
           /* SMSUtils.sendMessage("cola","",phone,code);*/

            // 将生成的验证码保存到 Session 中
            httpSession.setAttribute(phone,code);
            return R.success("短信验证码发送成功");
        }


        return R.error("短信验证码发送失败");

    }
    /**
     * 移动端登录
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession httpSession){

        log.info(map.toString());
        // 获取手机号
        String phone = map.get("phone").toString();
        // 获取验证码
        String code = map.get("code").toString();
        Object codeInSession = httpSession.getAttribute(phone);
        //校验
        if(codeInSession!=null && codeInSession.equals(code)){
            // 判断用户是否为新用户
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);

            if(user==null){
                 user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            httpSession.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登陆失败");


}
}
