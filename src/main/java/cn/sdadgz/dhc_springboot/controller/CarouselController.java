package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.Carousel;
import cn.sdadgz.dhc_springboot.mapper.CarouselMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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
@Slf4j
public class CarouselController {

    @Resource
    private CarouselMapper carouselMapper;

    @Resource
    private ICarouselService carouselService;

    @GetMapping
    public Result getCarousel() {
        return Result.success(carouselService.getAll());
    }

    @DeleteMapping
    public Result deleteBatch(@RequestBody Map<String, List<Integer>> requestMap) {

        List<Integer> idList = requestMap.get(MagicValueUtil.REQUEST_LISTS);
        int i = carouselMapper.deleteBatchIds(idList);
        log.info("轮播图删除了 {} 项", i);

        return Result.success();
    }

    // 获取分页
    @GetMapping("/page")
    public Result getPage(@RequestParam("currentPage") int currentPage,
                          @RequestParam("pageSize") int pageSize,
                          @RequestParam(value = "title", required = false) String title) throws ExecutionException, InterruptedException {

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
