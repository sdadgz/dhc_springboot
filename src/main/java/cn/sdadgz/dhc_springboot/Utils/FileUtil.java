package cn.sdadgz.dhc_springboot.Utils;

import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.config.FileConfig;
import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.mapper.FileMapper;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import cn.sdadgz.dhc_springboot.service.IFileService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class FileUtil {

    // 他自己
    private static FileUtil fileUtil;

    @PostConstruct
    public void init() {
        fileUtil = this;
    }

    @Value("${my.file-config.uploadPath}")
    private String uploadPath;

    @Value("${my.file-config.downloadPath}")
    private String downloadPath;

    @Resource
    private FileConfig fileConfig;

    @Resource
    private IEssayService essayService;

    @Resource
    private FileMapper fileMapper;

    @Resource
    private IFileService fileService;

    // 上传file
    public static cn.sdadgz.dhc_springboot.entity.File uploadFile(MultipartFile file, String token, String title) throws NoSuchAlgorithmException, IOException {

        // 初始化
        cn.sdadgz.dhc_springboot.entity.File uploadFile = new cn.sdadgz.dhc_springboot.entity.File();
        String originalFilename = file.getOriginalFilename();
        String type = getType(originalFilename);
        // 如果自定义标题
        if (!(title == null || title.equals(MagicValueUtil.EMPTY_STRING))) {
            originalFilename = title;
        }
        String username = IdUtil.getName(token);
        String uuid = IdUtil.uuid(file.getOriginalFilename() + token + title);

        // 创建文件夹
        String basePath = fileUtil.fileConfig.getFileUploadPath();
        boolean mkdirs = new File(basePath).mkdirs();
        log.info("创建文件夹{},{}", basePath, OtherUtil.bool(mkdirs));

        // 上传至服务器
        String uploadPath = basePath + username + MagicValueUtil.UNDERLINE + uuid + type;
        uploadToServer(file, uploadPath);
        File jFile = new File(uploadPath);
        String md5 = Md5Util.md5(jFile);

        // 去重
        cn.sdadgz.dhc_springboot.entity.File exists = fileUtil.fileService.exists(md5);
        if (exists != null) {
            uploadFile.setUrl(exists.getUrl());
            boolean delete = jFile.delete();
            log.info("重复文件{},删除{}", originalFilename, OtherUtil.bool(delete));
        } else {
            uploadFile.setUrl(toUrl(uploadPath));
        }

        // 上传数据库
        uploadFile.setOriginalFilename(fileUtil.fileService.nameExists(originalFilename));
        uploadFile.setMd5(md5);
        uploadFile.setCreateTime(TimeUtil.now());
        fileUtil.fileMapper.insert(uploadFile);

        return uploadFile;
    }

    // 根据url删除文件
    public static void deleteFileByUrl(String... url) {
        for (String s : url) {
            if (s != null && s.contains(fileUtil.downloadPath)) {
                String path = s.substring(fileUtil.downloadPath.length());
                path = fileUtil.uploadPath + path;
                File file = new File(path);
                boolean delete = file.delete();
                log.info("删除文件{},{}", path, delete ? "成功" : "失败");
            }
        }
    }

    // 路径纠正
    public static String pathCorrect(String path) {
        return path.replace('\\', '/');
    }

    // 路径转url
    public static String toUrl(String path) {
        // 替换反杠
        path = pathCorrect(path);

        // 包含上传路径
        if (path.contains(fileUtil.uploadPath)) {
            String substring = path.substring(fileUtil.uploadPath.length());
            path = fileUtil.downloadPath + substring;
        }

        return path;
    }

    // url转路径
    public static String toPath(String url) {
        // 替换反杠
        url = pathCorrect(url);

        // 包含上传路径
        if (url.contains(fileUtil.downloadPath)) {
            String substring = url.substring(fileUtil.downloadPath.length());
            url = fileUtil.uploadPath + substring;
        }

        return url;
    }

    // 上传到服务器
    public static void uploadToServer(MultipartFile file, String path) {
        if (!file.isEmpty()) {
            try {
                file.transferTo(new File(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 获取拓展名
    public static String getType(String fileName) {
        if (fileName == null) {
            throw new BusinessException("573", "获取不到文件名");
        }
        if (!fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    // 去拓展名
    public static String getName(String fileName) {
        if (fileName == null) {
            throw new BusinessException("533", "空文件名");
        }
        if (!fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    // 根据ids删除
    public static void deleteEssayByIds(List<Integer> ids) {
        List<Essay> essayList = fileUtil.essayService.getEssayByIds(ids);
        essayList.forEach(item -> {
            String dir = fileUtil.uploadPath + item.getDir();
            deleteDir(dir);
        });
    }

    // 删除文件夹
    public static boolean deleteDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                return false;
            }
            for (File f : files) {
                if (f.isFile()) {
                    if (!f.delete()) {
                        log.error("FileUtil::deleteDir  {} delete error", f.getAbsolutePath());
                        return false;
                    }
                } else {
                    if (!deleteDir(f.getAbsolutePath())) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    // 设置浓缩图 返回url 附默认值
    public static String setReduceImg(File file) throws IOException {
        return setReduceImg(file, MagicValueUtil.REDUCE_X, MagicValueUtil.REDUCE_Y);
    }

    // 设置浓缩图 返回url 防止空指针异常
    public static String setReduceImg(File file, Integer x, Integer y) throws IOException {
        if (x == null && y == null) {
            return setReduceImg(file);
        }
        return setReduceImg(file, x == null ? Integer.MAX_VALUE : x, y == null ? Integer.MAX_VALUE : y);
    }

    // 设置浓缩图 返回url
    public static String setReduceImg(File file, int reduceX, int reduceY) throws IOException {

        // 浓缩图路径
        String reducePath = file.getPath() + ".jpg";

        // 获取类型
        String name = reducePath.substring(fileUtil.uploadPath.length());
        String fileName = file.getName();
        String type = getType(fileName);
        if (containsType(type)) {
            BufferedImage image = ImageIO.read(file);
            int height = image.getHeight();
            int width = image.getWidth();
            if (width / height > reduceX / reduceY) {
                reduceX = width;
            } else {
                reduceY = height;
            }

            // 压缩
            Thumbnails.of(file).size(reduceX, reduceY).toFile(reducePath);

            // 返回路径
            return toUrl(reducePath);
        }

        return null;
    }

    // 包含可处理图片
    private static boolean containsType(String type) {
        for (String s : MagicValueUtil.IMG_SUPPORT_TYPE) {
            if (type.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
