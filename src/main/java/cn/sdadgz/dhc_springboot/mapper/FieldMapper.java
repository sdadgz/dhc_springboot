package cn.sdadgz.dhc_springboot.mapper;

import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.Field;
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
 * @since 2022-09-30
 */
@Mapper
public interface FieldMapper extends BaseMapper<Field> {

}
