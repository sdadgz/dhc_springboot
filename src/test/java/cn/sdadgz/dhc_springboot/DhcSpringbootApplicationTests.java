package cn.sdadgz.dhc_springboot;

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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootTest
class DhcSpringbootApplicationTests {

    @Test
    void contextLoads() {

    }

    // html增加图片
    void htmlAddImg() {
        String filePath = "D:/下载/a.html";
        String imgPrefix = "<img ";
        String imgSuffix = " />";

        StringBuilder s = new StringBuilder(readHTML(filePath));

        // 插入点
        int insertPoint = s.indexOf(imgPrefix);
        //删除点
        int deleteEndPoint = s.indexOf(imgSuffix, insertPoint) +  imgSuffix.length();

        s.delete(insertPoint, deleteEndPoint);

        s.insert(insertPoint, "<img src=\"http://localhost:8002/static/banner.png\" />");

        overHtml(new File(filePath), s.toString());

        System.out.println(s);
    }

    // 重写html
    void overHtml(File file, String str) {
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
    public static String readHTML(String filepath) {
        StringBuilder htmlSb = new StringBuilder();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
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
        return htmlSb.toString();
    }

    // 提取图片
    void docxToImage() throws IOException {
        String filePath = "D:/下载/a.docx";
        String out = "D:/下载/a/";


        XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
        // 用XWPFDocument的getAllPictures来获取所有的图片
        List<XWPFPictureData> picList = document.getAllPictures();
        for (XWPFPictureData pic : picList) {
            System.out.println(pic.getPictureType() + pic.getFileName());
            byte[] bytev = pic.getData();
//                System.out.println(bytev.length);
            // 大于1000bites的图片我们才弄下来，消除word中莫名的小图片的影响
            if (bytev.length > 300) {
                FileOutputStream fos = new FileOutputStream(out + pic.getFileName());
                fos.write(bytev);
            }
        }
    }

    // docx转html
    void docxToHtml() throws Docx4JException, IOException {
        String filePath = "D:/下载/a.docx";
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(filePath));
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
        new FileOutputStream("D:/下载/a.html").write(htmlToXhtml(content).getBytes(StandardCharsets.UTF_8));
    }

    static String htmlToXhtml(String html) {
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);
        return doc.html();
    }

    /**
     * 替换的内容
     *
     * @return
     */
    static Map<String, String> getReplaceContent() {
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
