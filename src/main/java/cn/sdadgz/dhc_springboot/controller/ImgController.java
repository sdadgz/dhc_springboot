package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.*;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.config.FileConfig;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.ImgMapper;
import cn.sdadgz.dhc_springboot.service.IImgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-09-27
 */
@Slf4j
@RestController
@RequestMapping("/img")
public class ImgController {

    @Resource
    private FileConfig fileConfig;

    @Resource
    private IImgService imgService;

    @Resource
    private ImgMapper imgMapper;

    // 获取图片
    @GetMapping("/page")
    public Result getPage(@RequestParam("currentPage") int currentPage,
                          @RequestParam("pageSize") int pageSize) {

        Map<String, Object> map = imgService.getPage(currentPage, pageSize);

        return Result.success(map);
    }

    // 上传图片
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file,
                         @RequestParam(value = "reduceX", required = false) Integer reduceX,
                         @RequestParam(value = "reduceY", required = false) Integer reduceY,
                         @RequestHeader("token") String token) throws NoSuchAlgorithmException, IOException {

        Map<String, Object> map = imgService.uploadImg(file, reduceX, reduceY, token);

        return Result.success(map);
    }

    // 虚拟删除图片
    @DeleteMapping("")
    public Result delete(@RequestBody Map<String, List<Integer>> requestMap) {

        List<Integer> idList = requestMap.get(MagicValueUtil.REQUEST_LISTS);
        imgService.deleteImg(idList);

        return Result.success();
    }

}
