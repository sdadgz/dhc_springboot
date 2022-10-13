package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.FriendLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-13
 */
@Mapper
public interface FriendLinkMapper extends BaseMapper<FriendLink> {

    // 获取分页
    List<FriendLink> getPage(@Param("startPage")int startPage,
                             @Param("pageSize")int pageSize);

}
