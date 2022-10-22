package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 获取用户
    List<User> getPage(@Param("startPage") int startPage,
                       @Param("pageSize") int pageSize,
                       @Param("name") String name);

}
