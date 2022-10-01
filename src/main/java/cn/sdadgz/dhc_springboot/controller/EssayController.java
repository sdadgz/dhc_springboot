package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.DocxUtil;
import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.IdUtil;
import cn.sdadgz.dhc_springboot.Utils.TimeUtil;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.Field;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.mapper.FieldMapper;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import cn.sdadgz.dhc_springboot.service.IFieldService;
import cn.sdadgz.dhc_springboot.service.IImgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.TileObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.sdadgz.dhc_springboot.Utils.StringUtil.*;

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
@RequestMapping("/essay")
public class EssayController {

    @Value("${my.file-config.uploadPath}")
    private String uploadPath;

    @Value("${my.file-config.downloadPath}")
    private String downloadPath;

    @Resource
    private EssayMapper essayMapper;

    @Resource
    private IImgService imgService;

    @Resource
    private IFieldService fieldService;

    @Resource
    private FieldMapper fieldMapper;

    @Resource
    private IEssayService essayService;

    // 获取essay
    @GetMapping("/page")
    public Result getEssay(@RequestParam("field") String field,
                           @RequestParam("currentPage") int currentPage,
                           @RequestParam("pageSize") int pageSize) {

        // 初始化
        HashMap<String, Object> map = new HashMap<>();

        // 根据field获取essay
        List<Essay> lists = essayService.getEssayPageByField(field, currentPage, pageSize);
        map.put(RESULT_LISTS, lists);

        // 获取总数
        Long total = essayService.getEssayTotalByField(field);
        map.put(RESULT_TOTAL, total);

        return Result.success(map);
    }

    // 上传文章
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file,
                         @RequestParam("field") String field,
                         HttpServletRequest request) throws IOException, Docx4JException {

        // 初始化
        Map<String, Object> map = new HashMap<>();

        // 处理docx
        Essay essay = DocxUtil.upload(file, request);
        map.put("essay", essay);

        // 增加领域
        Integer suc = fieldService.setField(essay.getId(), field);
        map.put("addField", suc);

        return Result.success(map);
    }

}
