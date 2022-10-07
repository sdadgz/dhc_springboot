package cn.sdadgz.dhc_springboot.Utils;

import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.service.IEssayService;
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
    private IEssayService essayService;

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

            // 压缩
            Thumbnails.of(file).size(reduceX, reduceY).toFile(reducePath);

            // 返回路径
            return fileUtil.downloadPath + name;
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
