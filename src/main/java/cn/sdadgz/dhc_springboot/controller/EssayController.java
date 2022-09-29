package cn.sdadgz.dhc_springboot.controller;

import cn.sdadgz.dhc_springboot.Utils.DocxUtil;
import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.IdUtil;
import cn.sdadgz.dhc_springboot.Utils.TimeUtil;
import cn.sdadgz.dhc_springboot.common.Result;
import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.service.IImgService;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.sdadgz.dhc_springboot.Utils.StringUtil.LEVER;

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

    private final String UNDEFINED = "未定义";
    private final String[] SUPPORT_TYPE = {".docx"}; // 支持的文件类型
    private final String HTML_SUFFIX = ".html"; // html文件后缀

    // 上传文章
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file,
                         @RequestParam("field") String field,
                         HttpServletRequest request) throws IOException, Docx4JException {

        // 初始化
        Essay essay = new Essay();
        essay.setCreateTime(TimeUtil.now());

        // 原文件名
        String originalFilename = file.getOriginalFilename();
        if (!isSupport(originalFilename)) {
            throw new BusinessException("442", "尚未支持该文件类型");
        }

        // 标题和用户id
        String title = FileUtil.getName(originalFilename);
        int userId = IdUtil.getId(request);
        essay.setTitle(title);
        essay.setUserId(userId);
        essay.setText(UNDEFINED);
        essayMapper.insert(essay);

        // 创建文件夹
        String basePath = uploadPath + title + LEVER;
        File baseDir = new File(basePath);
        if (!baseDir.exists()) {
            boolean mkdirs = baseDir.mkdirs();
            log.info("创建文件夹 {}，{}", title, mkdirs ? "成功" : "失败");
        }

        // 前端传的文件搞本地上
        String path = basePath + originalFilename;
        FileUtil.uploadToServer(file, path);

        // 处理docx
        String htmlPath = basePath + title + HTML_SUFFIX;
        DocxUtil.docxToHtml(path, htmlPath);
        DocxUtil.getImgsFromDocx(path, basePath);

        // 处理docx中的图片
        File[] files = baseDir.listFiles();
        if (files == null) {
            throw new BusinessException("587", "处理docx时创建的文件夹下无文件");
        }
        List<Img> imgs = new ArrayList<>();
        for (File f : files) {
            String s = f.getAbsolutePath();
            String translateStr = s.replace("\\", "/");
            if (!translateStr.equals(path) && !translateStr.equals(htmlPath)) {
                Img img = imgService.insertByPath(translateStr, essay.getId());
                imgs.add(img);
            }
        }

        // 图片数组搞到手了，整合
        String text = DocxUtil.replaceImg(new File(htmlPath), imgs);
        essay.setText(text);
        essayMapper.updateById(essay);

        return Result.success();
    }

    // 上传的文件类型是否支持
    private boolean isSupport(String filename) {
        String type = FileUtil.getType(filename);
        for (String s : SUPPORT_TYPE) {
            if (type.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
