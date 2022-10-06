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
import cn.sdadgz.dhc_springboot.mapper.UserMapper;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import cn.sdadgz.dhc_springboot.service.IFieldService;
import cn.sdadgz.dhc_springboot.service.IImgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
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
import java.security.NoSuchAlgorithmException;
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

    @Resource
    private UserMapper userMapper;

    // 删除essay
    @DeleteMapping("")
    public Result delete(@RequestParam("id")int id){



        return Result.success();
    }

    // 修改
    @PutMapping("/update")
    public Result update(@RequestParam(value = "title", required = false) String title,
                         @RequestParam(value = "field", required = false) String field,
                         @RequestParam("id") int id) {

        // 遣返
        if (title != null && field != null) {
            throw new BusinessException("400", "bad request");
        }

        // 初始化
        Map<String, Object> map = new HashMap<>();

        if (title != null) {
            // 修改标题
            Essay essay = new Essay();
            essay.setId(id);
            essay.setTitle(title);
            int i = essayMapper.updateById(essay);
            map.put(RESULT_STATUS, i > 0);
        } else if (field != null) {
            // 修改领域
            Field updateField = new Field();
            updateField.setId(id);
            updateField.setField(field);
            int i = fieldMapper.updateById(updateField);
            map.put(RESULT_STATUS, i > 0);
        }

        return Result.success(map);
    }

    // 获取text
    @GetMapping("/text")
    public Result getText(@RequestParam("id") int id) {

        // 初始化
        Map<String, Object> map = new HashMap<>();

        // 获取text
        Essay essay = essayMapper.selectById(id);
        essay.setUser(userMapper.selectById(essay.getUserId()));
        map.put(RESULT_ESSAY, essay);
        map.put(RESULT_TEXT, essay.getText());

        return Result.success(map);
    }

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

        // 返回text
        if (total == 1) {
            map.put(RESULT_TEXT, lists.get(0).getText());
        }

        return Result.success(map);
    }

    // 上传文章
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file,
                         @RequestParam("field") String field,
                         HttpServletRequest request) throws IOException, Docx4JException, NoSuchAlgorithmException {

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
