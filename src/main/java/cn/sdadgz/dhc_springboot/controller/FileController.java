package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.MagicValueUtil;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.entity.File;
import cn.sdadgz.dhc_springboot.service.IFileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sdadgz
 * @since 2022-10-16
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private IFileService fileService;

    // 获取分页
    @GetMapping("/page")
    public Result page(@RequestParam("currentPage") int currentPage,
                       @RequestParam("pageSize") int pageSize,
                       @RequestParam(value = "title", required = false) String title) {

        Map<String, Object> map = fileService.getPage(currentPage, pageSize, title);

        return Result.success(map);
    }

    // 上传
    @PostMapping("/upload")
    public Result upload(@RequestPart MultipartFile file,
                         @RequestParam(value = "title", required = false) String title,
                         @RequestHeader("token") String token) throws NoSuchAlgorithmException, IOException {

        File resultFile = FileUtil.uploadFile(file, token, title);

        return Result.success(resultFile);
    }

    // 删除
    @DeleteMapping
    public Result delete(@RequestBody Map<String, List<Integer>> requestMap) {

        List<Integer> idList = requestMap.get(MagicValueUtil.REQUEST_LISTS);
        fileService.updateIsDelete(idList);

        return Result.success();
    }

}
