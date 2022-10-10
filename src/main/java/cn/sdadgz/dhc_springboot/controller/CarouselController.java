package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.Carousel;
import cn.sdadgz.dhc_springboot.mapper.CarouselMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
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

    @Resource
    private ICarouselService carouselService;

    // 获取分页
    @GetMapping("/page")
    public Result getPage(@RequestParam("currentPage") int currentPage,
                          @RequestParam("pageSize") int pageSize,
                          @RequestParam(value = "title", required = false) String title) {

        Map<String, Object> map = carouselService.getPage(currentPage, pageSize, title);

        return Result.success(map);
    }

    // 上传轮播图
    @PostMapping("/upload")
    public Result upload(@RequestBody Carousel carousel) {

        carouselMapper.insert(carousel);

        return Result.success();
    }

}
