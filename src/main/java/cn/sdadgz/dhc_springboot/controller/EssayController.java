package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@RestController
@RequestMapping("/essay")
public class EssayController {

    // 上传文章
    public Result upload(){

        return Result.success();
    }

}
