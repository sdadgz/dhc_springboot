package cn.sdadgz.dhc_springboot.scheduled;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.entity.File;
import cn.sdadgz.dhc_springboot.mapper.FileMapper;
import cn.sdadgz.dhc_springboot.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class FileScheduled {

    @Resource
    private FileMapper fileMapper;

    @Resource
    private IFileService fileService;

    @Scheduled(cron = "0 */2 * ? * *")
    public void deleteFile() {
        log.info("每周六早上6点6分6秒定时清理文件");
        List<File> deleteFiles = fileMapper.getDeleteFiles();

        if (deleteFiles.size() > 0) {
            log.info("开始清理 {} 个文件", deleteFiles.size());
            for (File deleteFile : deleteFiles) {
                String url = deleteFile.getUrl();
                FileUtil.deleteFileByUrl(url);
            }

            // 数据库删除
            fileService.deleteByMD5(deleteFiles);
        }else {
            log.info("没有文件可以清理");
        }

    }
}
