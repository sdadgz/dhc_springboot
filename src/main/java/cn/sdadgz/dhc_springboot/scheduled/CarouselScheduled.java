package cn.sdadgz.dhc_springboot.scheduled;


import cn.sdadgz.dhc_springboot.Utils.OtherUtil;
import cn.sdadgz.dhc_springboot.entity.Carousel;
import cn.sdadgz.dhc_springboot.mapper.CarouselMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class CarouselScheduled {

    @Resource
    private CarouselMapper carouselMapper;

    @Resource
    private ICarouselService carouselService;

    @Scheduled(cron = "11 45 14 ? * 6")
    public void deleteCarousel() {
        // 删除冗余的轮播图
        log.info("开始删除冗余的轮播图");
        List<Carousel> gc = carouselService.getGC();
        int i = carouselMapper.deleteBatchIds(gc);
        log.info("获取到{}条垃圾，清理{}", gc.size(), OtherUtil.bool(i > 0));
    }

}
