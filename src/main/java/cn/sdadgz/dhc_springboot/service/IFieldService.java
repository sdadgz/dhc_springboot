package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.Field;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
    Map<String, Object> getField(String field, int currentPage, int pageSize);

    // 根据field获取总数
    Long getCount(String field);

    // 根据essayId删除
    void deleteByEssayIds(List<Integer> ids);

    // 同步field
    void synchronousField();
}
