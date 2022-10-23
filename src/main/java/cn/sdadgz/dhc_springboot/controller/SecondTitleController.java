package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.SecondTitle;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import cn.sdadgz.dhc_springboot.service.ISecondTitleService;
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
@RequestMapping("/secondTitle")
public class SecondTitleController {

    @Resource
    private SecondTitleMapper secondTitleMapper;

    @Resource
    private ISecondTitleService secondTitleService;

    // 上传
    @PostMapping
    public Result upload(@RequestBody SecondTitle secondTitle) {

        int insert = secondTitleMapper.insert(secondTitle);

        return Result.success(insert);
    }

    @GetMapping
    public Result getPage(@RequestParam("currentPage") int currentPage,
                          @RequestParam("pageSize") int pageSize,
                          @RequestParam(value = "title", required = false) String title) {

        Map<String, Object> map = secondTitleService.getPage(currentPage, pageSize, title);

        return Result.success(map);
    }

}
