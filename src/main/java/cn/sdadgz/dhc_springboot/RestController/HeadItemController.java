package cn.sdadgz.dhc_springboot.RestController;

import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/headItem")
public class HeadItemController {

    @Resource
    private SecondTitleMapper secondTitleMapper;

    @GetMapping
    public Result getAll() {

        List<cn.sdadgz.dhc_springboot.Dto.HeadItem> all = secondTitleMapper.getAll();

        return Result.success(all);
    }
}
