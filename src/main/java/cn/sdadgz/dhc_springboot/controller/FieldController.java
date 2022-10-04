package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.Field;
import cn.sdadgz.dhc_springboot.mapper.FieldMapper;
import cn.sdadgz.dhc_springboot.service.IFieldService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-30
 */
@RestController
@RequestMapping("/field")
public class FieldController {

    @Resource
    private FieldMapper fieldMapper;

    @Resource
    private IFieldService fieldService;

    // 模糊查询获取essay
    @GetMapping("")
    public Result getEssays(@RequestParam("field") String field,
                            @RequestParam("currentPage") Integer currentPage,
                            @RequestParam("pageSize") Integer pageSize) {

        List<Field> list = fieldService.getField(field, currentPage, pageSize);

        return Result.success(list);
    }

}
