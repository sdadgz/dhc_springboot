package cn.sdadgz.dhc_springboot.scheduled;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.ImgMapper;
import cn.sdadgz.dhc_springboot.service.IImgService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class ImgScheduled {

    @Resource
    private ImgMapper imgMapper;

    @Resource
    private IImgService imgService;

    // 定期物理删除
    @Scheduled(cron = "11 45 14 ? * 6")
    private void deleteImg() {
        log.info("每周六下午2点45分11秒清理删除的图片");
        List<Img> deleteImgs = imgMapper.getDeleteImgs();
        if (deleteImgs.size() < 1){
            return;
        }
        for (Img deleteImg : deleteImgs) {
            String url = deleteImg.getUrl();
            String reduceUrl = deleteImg.getReduceUrl();

            FileUtil.deleteFileByUrl(url, reduceUrl);
        }

        // 数据库删除
        imgService.deleteByMD5(deleteImgs);
    }

}
