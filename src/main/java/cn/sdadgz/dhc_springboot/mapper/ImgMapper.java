package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.Img;
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
 * @since 2022-09-27
 */
@Mapper
public interface ImgMapper extends BaseMapper<Img> {

    // 根据essayId获取img
    List<Img> getPage(@Param("essayId") Integer essayId,
                      @Param("startPage") int startPage,
                      @Param("pageSize") int pageSize,
                      @Param("title") String title);

}
