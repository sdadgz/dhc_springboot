package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.Field;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.mapper.FieldMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import cn.sdadgz.dhc_springboot.service.IFieldService;
import cn.sdadgz.dhc_springboot.service.IImgService;
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

    @Resource
    private IFieldService fieldService;

    @Resource
    private IImgService imgService;

    @Resource
    private ICarouselService carouselService;

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

    @Override
    public void deleteAllByIds(List<Integer> idList) {
        removeByIds(idList); // 删除essay表数据
        fieldService.deleteByEssayIds(idList); // 删除field表数据
        imgService.deleteByEssayIds(idList); // 删除img表数据
        carouselService.deleteByEssayIds(idList); // 删除轮播图
    }

    @Override
    public List<Essay> getEssayByIds(List<Integer> ids) {
        LambdaQueryWrapper<Essay> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Essay::getId, ids);
        return essayMapper.selectList(wrapper);
    }

}
