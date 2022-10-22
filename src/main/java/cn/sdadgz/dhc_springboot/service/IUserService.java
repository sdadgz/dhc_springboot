package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-26
 */
public interface IUserService extends IService<User> {

    // 验证密码
    boolean verifyPassword(User user) throws NoSuchAlgorithmException;

    // 根据name获取用户
    User getUserByName(String name);

    // 获取分页
    Map<String, Object> getPage(int currentPage, int pageSize, String name);

}
