package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.File;
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
 * @since 2022-10-16
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

    // 获取分页
    List<File> getPage(@Param("startPage") int startPage,
                       @Param("pageSize") int pageSize,
                       @Param("title") String title);

}
