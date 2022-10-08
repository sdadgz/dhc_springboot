package cn.sdadgz.dhc_springboot.Utils;

import cn.sdadgz.dhc_springboot.config.BusinessException;
import cn.sdadgz.dhc_springboot.entity.Essay;
import cn.sdadgz.dhc_springboot.entity.Img;
import cn.sdadgz.dhc_springboot.mapper.EssayMapper;
import cn.sdadgz.dhc_springboot.service.IImgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.sdadgz.dhc_springboot.Utils.MagicValueUtil.*;

@Slf4j
@Component
public class DocxUtil {

    private static DocxUtil docxUtil;

    @PostConstruct
    public void init() {
        docxUtil = this;
    }

    @Value("${my.file-config.uploadPath}")
    private String uploadPath;

    @Value("${my.file-config.downloadPath}")
    private String downloadPath;

    @Resource
    private EssayMapper essayMapper;

    @Resource
    private IImgService imgService;

    // 上传essay
    public static Essay upload(MultipartFile file, HttpServletRequest request) throws IOException, NoSuchAlgorithmException, Docx4JException {
        String name = file.getName();
        return upload(file, request, name);
    }

    // 封装的上传
    public static Essay upload(MultipartFile file, HttpServletRequest request, String requestTitle) throws IOException, Docx4JException, NoSuchAlgorithmException {
        // 初始化
        Essay essay = new Essay();
        essay.setCreateTime(TimeUtil.now());
        essay.setText(UNDEFINED);

        // 文件夹
        String uuid = IdUtil.uuid();
        String requestUsername = IdUtil.getName(request);
        String dir = requestUsername + UNDERLINE + uuid;
        essay.setDir(dir);

        // 原文件名
        String originalFilename = file.getOriginalFilename();
        if (!isSupport(originalFilename)) {
            throw new BusinessException("542", "尚未支持该文件类型");
        }

        // 标题和用户id
        int userId = IdUtil.getId(request);
        essay.setTitle(requestTitle);
        essay.setUserId(userId);

        // 创建文件夹
        String basePath = docxUtil.uploadPath + dir + LEVER;
        File baseDir = new File(basePath);
        if (!baseDir.exists()) {
            boolean mkdirs = baseDir.mkdirs();
            log.info("创建文件夹 {}，{}", basePath, mkdirs ? "成功" : "失败");
        }

        // 前端传的文件搞本地上
        String path = basePath + originalFilename;
        String htmlPath = basePath + requestTitle + HTML_SUFFIX;
        FileUtil.uploadToServer(file, path);
        docxUtil.essayMapper.insert(essay);

        // 处理docx
        DocxUtil.docxToHtml(path, htmlPath);
        DocxUtil.getImgsFromDocx(path, basePath);

        // 处理docx中的图片
        File[] files = baseDir.listFiles();
        if (files == null) {
            throw new BusinessException("587", "处理docx时创建的文件夹下无文件");
        }
        List<Img> imgs = new ArrayList<>();
        for (File f : files) {
            String absolutePath = f.getAbsolutePath();
            absolutePath = FileUtil.pathCorrect(absolutePath);
            if (!absolutePath.equals(path) && !absolutePath.equals(htmlPath)) {
                Img img = docxUtil.imgService.insertByPath(absolutePath, essay.getId());
                imgs.add(img);
            }
        }

        // 图片拿过来，搞里头
        String html = DocxUtil.replaceImg(new File(htmlPath), imgs);
        essay.setText(html);
        docxUtil.essayMapper.updateById(essay);

        return essay;
    }

    // 上传的文件类型是否支持
    private static boolean isSupport(String filename) {
        String type = FileUtil.getType(filename);
        for (String s : ESSAY_SUPPORT_TYPE) {
            if (type.equals(s)) {
                return true;
            }
        }
        return false;
    }

    // 从docx中提取图片
    public static void getImgsFromDocx(String path, String outDir) throws IOException {
        getImgsFromDocx(new File(path), outDir);
    }

    // 从docx中提取图片
    public static void getImgsFromDocx(File file, String outDir) throws IOException {
        XWPFDocument document = new XWPFDocument(new FileInputStream(file));
        // 用XWPFDocument的getAllPictures来获取所有的图片
        List<XWPFPictureData> picList = document.getAllPictures();
        for (XWPFPictureData pic : picList) {
            System.out.println(pic.getPictureType() + pic.getFileName());
            byte[] bytev = pic.getData();
//                System.out.println(bytev.length);
            // 大于1000bites的图片我们才弄下来，消除word中莫名的小图片的影响
            if (bytev.length > 300) {
                FileOutputStream fos = new FileOutputStream(outDir + pic.getFileName());
                fos.write(bytev);
            }
        }
    }

    // 将生成的html文件中的图片替换
    public static String replaceImg(File file, List<Img> imgs) {
        // 才没有在骂人
        StringBuilder sb = readHtml(file);
        // 图片索引
        int index = 0;
        int startIndex = 0;

        for (int i = 0; i < imgs.size(); i++) {
            // 插入点
            int insertPoint = sb.indexOf(IMG_PREFIX, startIndex);
            // 删除点
            int deleteEndPoint = sb.indexOf(IMG_SUFFIX, insertPoint) + IMG_SUFFIX.length();
            // 删除
            sb.delete(insertPoint, deleteEndPoint);
            // 插入
            String insertStr = OVERRIDE_IMG_PREFIX + imgs.get(index++).getUrl() + OVERRIDE_IMG_SUFFIX;
            sb.insert(insertPoint, insertStr);
            // 更新开始索引
            startIndex = insertPoint + insertStr.length();
        }

        // 重写html文件
        overHtml(file, sb.toString());

        // 返回html
        return sb.toString();
    }

    // 重写html
    private static void overHtml(File file, String str) {
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(str);
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert output != null;
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 读取html文件
    private static StringBuilder readHtml(File file) {
        StringBuilder htmlSb = new StringBuilder();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while (br.ready()) {
                htmlSb.append(br.readLine()).append('\n'); // 换行，方便查看
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return htmlSb;
    }

    public static void docxToHtml(String path, String toPath) throws IOException, Docx4JException {
        docxToHtml(new File(path), toPath);
    }

    // 转为html，复制的
    public static void docxToHtml(File file, String toPath) throws Docx4JException, IOException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(file);
        OutputStream os = new ByteArrayOutputStream();
        HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
        htmlSettings.setUserBodyTail("");
        htmlSettings.setUserBodyTop("");
        htmlSettings.setOpcPackage(wordMLPackage);
        Docx4J.toHTML(htmlSettings, os, Docx4J.FLAG_NONE);
        String content = os.toString();
        //默认样式替换
        int start = content.indexOf("<style><!--");
        int end = content.indexOf("--></style>");
        Map<String, String> replaceMap = getReplaceContent();
        if (start > 0 && end > 0) {
            replaceMap.put(content.substring(start + 7, end + 3), replaceMap.get("style"));
        }
        replaceMap.remove("style");

        for (Map.Entry<String, String> item : replaceMap.entrySet()) {
            content = content.replace(item.getKey(), item.getValue());
        }
        new FileOutputStream(toPath).write(htmlToXhtml(content).getBytes(StandardCharsets.UTF_8));
    }

    // 复制的
    private static String htmlToXhtml(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);
        return doc.html();
    }

    // 复制的
    private static Map<String, String> getReplaceContent() {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("style", "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img,  table, caption, tbody, tfoot, thead, tr, th, td " +
                "{ margin: 0; padding: 0; border: 0;}" +
                "body {line-height: 1.5;} ");

        replaceMap.put("TO HIDE THESE MESSAGES, TURN OFF debug level logging for org.docx4j.convert.out.common.writer.AbstractMessageWriter", "");
        replaceMap.put("<script type=\"text/javascript\"><!--function toggleDiv(divid){if(document.getElementById(divid).style.display == 'none'){document.getElementById(divid).style.display = 'block';}else{document.getElementById(divid).style.display = 'none';}}\n" +
                "--></script>", "");
        return replaceMap;
    }

}
