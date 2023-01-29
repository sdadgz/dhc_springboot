package cn.sdadgz.dhc_springboot.service.impl;

import cn.sdadgz.dhc_springboot.Utils.GeneralUtil;
import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.entity.Carousel;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.CarouselMapper;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.mapper.ImgMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.wml.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@Slf4j
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Resource
    private ImgMapper imgMapper;

    @Resource
    private EssayMapper essayMapper;

    @Override
    public void deleteByEssayIds(List<Integer> idList) {
        LambdaQueryWrapper<Carousel> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Carousel::getEssayId, idList);
        carouselMapper.delete(wrapper);
    }

    @Override
    public Map<String, Object> getPage(int currentPage, int pageSize, String title) throws ExecutionException, InterruptedException {

        // 初始化
        Map<String, Object> map = new HashMap<>();
        int startPage = (currentPage - 1) * pageSize;

        // 获取
        List<Carousel> lists = carouselMapper.getPage(startPage, pageSize, title);
        long total = carouselMapper.getCount(title);

        // 入
        map.put(MagicValueUtil.RESULT_LISTS, lists);
        map.put(MagicValueUtil.RESULT_TOTAL, total);

        return map;
    }

    @Override
    public List<Carousel> getAll() {
        return carouselMapper.getAllCarousel();
    }

    @Override
    public List<Carousel> getGC() {
        // 因为一二级标题不可能超过500，数量很小，所以这里偷懒了
        List<Carousel> allCarousel = carouselMapper.selectList(null);
        List<Carousel> deleteCarousel = new ArrayList<>();
        allCarousel.forEach(carousel -> {
            if (GeneralUtil.isNull(imgMapper.selectById(carousel.getImgId()))) {
                deleteCarousel.add(carousel);
            } else if (GeneralUtil.isNull(essayMapper.selectById(carousel.getEssayId()))) {
                deleteCarousel.add(carousel);
            }

        });
        return deleteCarousel;
    }

}
