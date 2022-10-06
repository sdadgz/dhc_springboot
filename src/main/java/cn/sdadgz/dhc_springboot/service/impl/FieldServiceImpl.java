package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.entity.Field;
import cn.sdadgz.dhc_springboot.mapper.FieldMapper;
import cn.sdadgz.dhc_springboot.service.IFieldService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.sdadgz.dhc_springboot.Utils.StringUtil.RESULT_LISTS;
import static cn.sdadgz.dhc_springboot.Utils.StringUtil.RESULT_TOTAL;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-30
 */
@Service
public class FieldServiceImpl extends ServiceImpl<FieldMapper, Field> implements IFieldService {

    @Resource
    private FieldMapper fieldMapper;

    // 设置领域
    @Override
    public Integer setField(int essayId, String field) {
        Field uploadObj = new Field();
        uploadObj.setEssayId(essayId);
        uploadObj.setField(field);
        fieldMapper.insert(uploadObj);
        return uploadObj.getId();
    }

    @Override
    public Map<String, Object> getField(String field, int currentPage, int pageSize) {

        // 初始化
        Map<String, Object> map = new HashMap<>();
        int startPage = (currentPage - 1) * pageSize;

        List<Field> lists = fieldMapper.getAllByField(field, startPage, pageSize);
        Long total = getCount(field);

        map.put(RESULT_LISTS, lists);
        map.put(RESULT_TOTAL, total);

        return map;
    }

    @Override
    public Long getCount(String field) {
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Field::getField, field);
        return fieldMapper.selectCount(wrapper);
    }

    @Override
    public void deleteByEssayIds(List<Integer> ids) {
        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Field::getEssayId, ids);
        fieldMapper.delete(wrapper);
    }

}
