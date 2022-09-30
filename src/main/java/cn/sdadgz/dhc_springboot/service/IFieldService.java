package cn.sdadgz.dhc_springboot.service;

import cn.sdadgz.dhc_springboot.entity.Field;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-30
 */
public interface IFieldService extends IService<Field> {

    // 设置文章领域
    Integer setField(int essayId,String field);

}
