package cn.sdadgz.dhc_springboot;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.JwtUtil;
import cn.sdadgz.dhc_springboot.Utils.UserUtil;
import cn.sdadgz.dhc_springboot.config.FileConfig;
import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.User;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import cn.sdadgz.dhc_springboot.service.IUserService;
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
import java.security.NoSuchAlgorithmException;
import java.util.*;

@SpringBootTest
class DhcSpringbootApplicationTests {

    @Resource
    private IEssayService essayService;

    @Resource
    private IUserService userService;

    @Resource
    private FileConfig fileConfig;

    @Resource
    private ICarouselService carouselService;

    @Test
    void contextLoads() {

    }

    @Test // 获取token
    void getToken() throws NoSuchAlgorithmException {
        String union = "1";
        String token = JwtUtil.createToken(union, union, UserUtil.getPassword(union));
        System.out.println(token);
    }

    @Test
    void getPage(){
        int currentPage = 1;
        int pageSize = 10;
        String title = null;
        Map<String, Object> page = carouselService.getPage(currentPage, pageSize, null);
        System.out.println(page);
    }

}
