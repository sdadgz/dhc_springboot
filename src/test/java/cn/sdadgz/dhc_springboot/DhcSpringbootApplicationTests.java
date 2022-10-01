package cn.sdadgz.dhc_springboot;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootTest
class DhcSpringbootApplicationTests {

    @Resource
    private IEssayService essayService;

    @Test
    void contextLoads() {

    }

    @Test
    void getEssayPageByField(){
        String field = "中心概况 组织框架";

        Long page = essayService.getEssayTotalByField(field);
        System.out.println(page);

    }
}
