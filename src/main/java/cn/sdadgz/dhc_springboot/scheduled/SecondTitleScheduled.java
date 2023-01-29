package cn.sdadgz.dhc_springboot.scheduled;

import cn.sdadgz.dhc_springboot.entity.SecondTitle;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import cn.sdadgz.dhc_springboot.service.ISecondTitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class SecondTitleScheduled {

    @Resource
    private ISecondTitleService secondTitleService;

    @Scheduled(cron = "14 31 2 ? * 1")
    public void delete() {
        // todo 删除冗余的二级标题
        log.info("开始清理冗余的二级标题");
        List<SecondTitle> gc = secondTitleService.getGC();
        boolean b = secondTitleService.removeBatchByIds(gc);
        log.info("获取{}条冗余二级标题，删除{}", gc.size(), b);
    }


}
