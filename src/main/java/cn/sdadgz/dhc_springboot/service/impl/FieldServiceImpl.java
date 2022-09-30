package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.entity.Field;
import cn.sdadgz.dhc_springboot.mapper.FieldMapper;
import cn.sdadgz.dhc_springboot.service.IFieldService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-30
 */
@Service
public class FieldServiceImpl extends ServiceImpl<FieldMapper, Field> implements IFieldService {

    @Resource
    private FieldMapper fieldMapper;

    @Override
    public Integer setField(int essayId, String field) {
        Field uploadObj = new Field();
        uploadObj.setEssayId(essayId);
        uploadObj.setField(field);
        fieldMapper.insert(uploadObj);
        return uploadObj.getId();
    }
}
