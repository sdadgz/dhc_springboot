package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.FirstTitle;
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
 * @since 2022-10-22
 */
@Mapper
public interface FirstTitleMapper extends BaseMapper<FirstTitle> {

    // 获取分页
    List<FirstTitle> getPage(@Param("startPage") int startPage,
                             @Param("pageSize") int pageSize,
                             @Param("title") String title);

}
