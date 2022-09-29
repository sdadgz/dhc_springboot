package cn.sdadgz.dhc_springboot.Utils;

import cn.sdadgz.dhc_springboot.config.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtil {

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
                }else {
                    if (!deleteDir(f.getAbsolutePath())){
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

}
