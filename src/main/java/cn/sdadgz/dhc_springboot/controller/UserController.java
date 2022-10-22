package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.*;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.entity.User;
import cn.sdadgz.dhc_springboot.mapper.UserMapper;
import cn.sdadgz.dhc_springboot.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @PostMapping("/register")
    public Result register(@RequestBody User user) throws NoSuchAlgorithmException {

        // 密码加密
        String password = UserUtil.getPassword(user.getPassword());
        user.setPassword(password);

        userMapper.insert(user);

        return Result.success();
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) throws NoSuchAlgorithmException {
        Map<String, Object> map = new HashMap<>();

        boolean verify = userService.verifyPassword(user);

        if (!verify) {
            throw new BusinessException("459", "用户名或密码错误");
        }

        String token = JwtUtil.createToken(user);
        String name = user.getName();

        map.put("token", token);
        map.put("username", name);

        return Result.success(map);
    }

    // 获取全部用户
    @GetMapping("/page")
    public Result page(@RequestParam("currentPage") int currentPage,
                       @RequestParam("pageSize") int pageSize,
                       @RequestParam(value = "name", required = false) String name) {

        Map<String, Object> map = userService.getPage(currentPage, pageSize, name);

        return Result.success(map);
    }

    // 重命名
    @PostMapping("/update")
    public Result update(@RequestBody User user) throws NoSuchAlgorithmException {
        if (user.getPassword() != null && !Objects.equals(user.getPassword(), MagicValueUtil.EMPTY_STRING)) {
            user.setPassword(UserUtil.getPassword(user.getPassword()));
        }
        userMapper.insert(user);
        return Result.success();
    }

}
