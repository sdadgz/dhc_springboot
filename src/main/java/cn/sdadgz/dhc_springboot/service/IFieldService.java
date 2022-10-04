package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.Field;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-30
 */
public interface IFieldService extends IService<Field> {

    // 设置文章领域
    Integer setField(int essayId, String field);

    // 根据field模糊查询分页
    List<Field> getField(String field,int currentPage,int pageSize);

}
