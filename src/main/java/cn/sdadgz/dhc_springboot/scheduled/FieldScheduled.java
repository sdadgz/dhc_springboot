package cn.sdadgz.dhc_springboot.scheduled;

import cn.sdadgz.dhc_springboot.service.IFieldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
@EnableScheduling
@Slf4j
public class FieldScheduled {

    @Resource
    private IFieldService fieldService;

    /**
     * 同步field根据一二级标题id<br/>
     * <p>
     * 之前设计的问题，存储的不是id而是具体内容，所以某个id的内容修改会引起大量数据需要修改
     */
    @Scheduled(cron = "15 20 8 ? * 7")
    public void updateField() {
        log.info("开始定期同步field");
        fieldService.synchronousField();
    }

}
