package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.Field;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.mapper.FieldMapper;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@Service
public class EssayServiceImpl extends ServiceImpl<EssayMapper, Essay> implements IEssayService {

    @Resource
    private EssayMapper essayMapper;

    @Resource
    private FieldMapper fieldMapper;

    // 获取essay分页根据field
    @Override
    public List<Essay> getEssayPageByField(String field, int currentPage, int pageSize) {

        // 初始化
        int startPage = (currentPage - 1) * pageSize;

        return essayMapper.getPage(field, startPage, pageSize);
    }

    // 获取essay总数根据field
    @Override
    public Long getEssayTotalByField(String field) {

        LambdaQueryWrapper<Field> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Field::getField, field);

        return fieldMapper.selectCount(wrapper);
    }

}
