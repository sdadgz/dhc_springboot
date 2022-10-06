package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.Carousel;
import cn.sdadgz.dhc_springboot.mapper.CarouselMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Resource
    private CarouselMapper carouselMapper;

    // 上传轮播图
    @PostMapping("/upload")
    public Result upload(@RequestBody Carousel carousel){

        carouselMapper.insert(carousel);

        return Result.success();
    }

}
