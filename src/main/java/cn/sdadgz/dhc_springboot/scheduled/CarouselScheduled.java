package cn.sdadgz.dhc_springboot.scheduled;


import cn.sdadgz.dhc_springboot.mapper.CarouselMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

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
        log.info("开始删除冗余的轮播图");
    }

}
