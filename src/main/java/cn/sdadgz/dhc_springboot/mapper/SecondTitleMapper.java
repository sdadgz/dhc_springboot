package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.Dto.HeadItem;
import cn.sdadgz.dhc_springboot.entity.SecondTitle;
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
 * @since 2022-10-22
 */
@Mapper
public interface SecondTitleMapper extends BaseMapper<SecondTitle> {

    // 获取分页
    List<SecondTitle> getPage(@Param("startPPage") int startPage,
                              @Param("pageSize")int pageSize,
                              @Param("title")String title);

    // 获取全部
    List<HeadItem> getAll();

}
