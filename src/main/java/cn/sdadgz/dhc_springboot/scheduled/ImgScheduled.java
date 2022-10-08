package cn.sdadgz.dhc_springboot.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class ImgScheduled {

    // 定期物理删除
    @Scheduled(cron = "11 45 14 * * ?")
    private void deleteImg() {
        log.info("下午2点45分11秒");
    }

//    @Scheduled(cron = "*/5 * * * * ?")
//    private void test(){
//        log.info("定时器。。。");
//    }

}
