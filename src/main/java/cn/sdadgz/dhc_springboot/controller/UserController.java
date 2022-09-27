package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.Md5Util;
import cn.sdadgz.dhc_springboot.Utils.UserUtil;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.User;
import cn.sdadgz.dhc_springboot.mapper.UserMapper;
import cn.sdadgz.dhc_springboot.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private IUserService userService;

    private final String SALT = "我真是个活废物，除了复制别人的代码还是复制别人的代码，这就是低级码农吗";

    @PostMapping("/register")
    public Result register(@RequestBody User user) throws NoSuchAlgorithmException {

        // 密码加密
        user.setPassword(Md5Util.md5(user.getPassword() + UserUtil.SALT));

        userMapper.insert(user);

        return Result.success();
    }

    // 登录
    @PostMapping("/login")
    public Result login() {

        return Result.success();
    }

}
