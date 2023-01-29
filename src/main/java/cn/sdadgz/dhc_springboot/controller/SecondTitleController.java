package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.SecondTitle;
import cn.sdadgz.dhc_springboot.entity.User;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import cn.sdadgz.dhc_springboot.service.IFieldService;
import cn.sdadgz.dhc_springboot.service.ISecondTitleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Resource
    private IFieldService fieldService;

    // 修改
    @PutMapping
    public Result update(@RequestBody SecondTitle secondTitle) {

        if (secondTitle.getTitle().equals(MagicValueUtil.EMPTY_STRING)) {
            secondTitle.setTitle(null);
        }

        int i = secondTitleMapper.updateById(secondTitle);

        fieldService.synchronousField();

        return Result.success(i);
    }

    // 上传
    @PostMapping
    public Result upload(@RequestBody SecondTitle secondTitle) {

        if (secondTitle.getTitle().equals(MagicValueUtil.EMPTY_STRING)) {
            secondTitle.setTitle(null);
        }

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

    // 删除
    @DeleteMapping
    public Result delete(@RequestBody Map<String, List<Integer>> requestMap) {

        List<Integer> idList = requestMap.get(MagicValueUtil.REQUEST_LISTS);
        int i = secondTitleMapper.deleteBatchIds(idList);

        return Result.success(i);
    }

}
