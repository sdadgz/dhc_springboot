package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@Mapper
public interface CarouselMapper extends BaseMapper<Carousel> {

    // 获取分页
    List<Carousel> getPage(@Param("startPage") int startPage,
                 @Param("pageSize") int pageSize,
                 @Param("title") String title);

    // 获取总数
    long getCount(@Param("title") String title);

}
