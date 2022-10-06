package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.entity.Carousel;
import cn.sdadgz.dhc_springboot.mapper.CarouselMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Override
    public void deleteByEssayIds(List<Integer> idList) {
        LambdaQueryWrapper<Carousel> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Carousel::getEssayId,idList);
        carouselMapper.delete(wrapper);
    }
}
