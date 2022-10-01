package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.Essay;
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
public interface EssayMapper extends BaseMapper<Essay> {

    // 获取分页
    List<Essay> getPage(@Param("field") String field,
                        @Param("startPage") int startPage,
                        @Param("pageSize") int pageSize);

}
