package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.Utils.UserUtil;
import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.entity.User;
import cn.sdadgz.dhc_springboot.mapper.UserMapper;
import cn.sdadgz.dhc_springboot.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    // 验证密码
    @Override
    public boolean verifyPassword(User user) throws NoSuchAlgorithmException {
        User databaseUser = getUserByName(user.getName());
        // 前端密码加密
        String md5Password = UserUtil.getPassword(user.getPassword());
        user.setPassword(databaseUser.getPassword());
        user.setId(databaseUser.getId());

        // 密码错误
        return md5Password.equals(databaseUser.getPassword());
    }

    @Override
    public User getUserByName(String name) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, name);
        List<User> users = userMapper.selectList(wrapper);

        if (users.size() != 1) {
            throw new BusinessException("453", "未找到用户");
        }

        return users.get(0);
    }

    @Override
    public Map<String, Object> getPage(int currentPage, int pageSize, String name) {

        Map<String, Object> map = new HashMap<>();
        int startPage = (currentPage - 1) * pageSize;

        List<User> lists = userMapper.getPage(startPage, pageSize, name);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, User::getName, name);
        Long total = userMapper.selectCount(wrapper);

        map.put(MagicValueUtil.RESULT_LISTS, lists);
        map.put(MagicValueUtil.RESULT_TOTAL, total);

        return map;
    }
}
