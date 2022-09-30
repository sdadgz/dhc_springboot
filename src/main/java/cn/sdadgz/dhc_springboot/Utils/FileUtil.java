package cn.sdadgz.dhc_springboot.Utils;

import cn.sdadgz.dhc_springboot.config.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

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

}
