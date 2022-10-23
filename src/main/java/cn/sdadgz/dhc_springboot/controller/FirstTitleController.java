package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.FirstTitle;
import cn.sdadgz.dhc_springboot.mapper.FirstTitleMapper;
import cn.sdadgz.dhc_springboot.service.IFirstTitleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/firstTitle")
public class FirstTitleController {

    @Resource
    private FirstTitleMapper firstTitleMapper;

    @Resource
    private IFirstTitleService firstTitleService;

    // 上传
    @PostMapping
    public Result upload(@RequestBody FirstTitle firstTitle) {

        firstTitleMapper.insert(firstTitle);

        return Result.success();
    }

    // 获取
    @GetMapping
    public Result get(@RequestParam("currentPage") int currentPage,
                      @RequestParam("pageSize") int pageSize,
                      @RequestParam(value = "title", required = false) String title) {

        Map<String, Object> map = firstTitleService.getPage(currentPage, pageSize, title);

        return Result.success(map);
    }

}
