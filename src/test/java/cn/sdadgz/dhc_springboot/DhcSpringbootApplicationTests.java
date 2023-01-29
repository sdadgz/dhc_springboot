package cn.sdadgz.dhc_springboot;

import cn.sdadgz.dhc_springboot.Utils.FileUtil;
import cn.sdadgz.dhc_springboot.Utils.JwtUtil;
import cn.sdadgz.dhc_springboot.Utils.UserUtil;
import cn.sdadgz.dhc_springboot.config.FileConfig;
import cn.sdadgz.dhc_springboot.entity.*;
import cn.sdadgz.dhc_springboot.mapper.FieldMapper;
import cn.sdadgz.dhc_springboot.mapper.ImgMapper;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import cn.sdadgz.dhc_springboot.service.ICarouselService;
import cn.sdadgz.dhc_springboot.service.IEssayService;
import cn.sdadgz.dhc_springboot.service.IImgService;
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

    @Resource
    private ImgMapper imgMapper;

    @Resource
    private IImgService imgService;

    @Resource
    private SecondTitleMapper secondTitleMapper;

    @Resource
    private FieldMapper fieldMapper;

    @Test
    void contextLoads() {

    }

    // 获取token
    @Test
    void getToken() throws NoSuchAlgorithmException {
        String union = "1";
        String token = JwtUtil.createToken(union, union, UserUtil.getPassword(union));
        System.out.println(token);
    }
}
