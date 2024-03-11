package com.example.mall.controller;


import com.example.mall.consts.MallConst;
import com.example.mall.enums.RoleEnum;
import com.example.mall.form.UserLoginForm;
import com.example.mall.form.UserRegisterForm;
import com.example.mall.pojo.User;
import com.example.mall.service.IUserService;
import com.example.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

import static com.example.mall.enums.ResponseEnum.PARAM_ERROR;

@RestController
@RequestMapping
@Slf4j
public class UserController {
//    这种写法是针对post通过x-www-form-urlencoded传参的写法
//    @PostMapping("/register")
//    public Void register(@RequestParam String username){
//        log.info("username={}", username);
//        return null;
//    }


    @Autowired
    private IUserService userService;

    @PostMapping("/user/register") //@RequestBody: 将请求的参数转换为对应的对象, 不加的话通过raw json格式传参不能通过，但可以x-www-form-urlencoded传参
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm, // @Valid: 对userForm进行校验
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("注册提交的参数有误, {}{}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(PARAM_ERROR,
                    bindingResult.getFieldError().getField() +
                    bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        user.setRole(RoleEnum.CUSTOMER.getCode());
        // DTO, 继承entity
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult,
                                  HttpSession session){
        if (bindingResult.hasErrors()){
            return ResponseVo.error(PARAM_ERROR, bindingResult);
        }
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        // 设置session
        session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());

        log.info("/login login session = {}", session.getId());
        return userResponseVo;
    }

    // session保存在内存里，重启后就没有了，所以要用token + redis
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        log.info("/user login session = {}", session.getId());

        return ResponseVo.success(user);
    }

    // TODO 判断登录状态, 拦截器
    /**
     * {@link TomcatServletWebServerFactory} getSessionTimeoutInMinutes
     */
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){
        log.info("/logout login session = {}", session.getId());
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.successByMsg("退出成功");
    }
}
