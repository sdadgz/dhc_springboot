package cn.sdadgz.dhc_springboot.Utils;

import cn.sdadgz.dhc_springboot.entity.Img;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocxUtil {

    private static final String IMG_PREFIX = "<img "; // 修改前的供查找的图片前缀
    private static final String IMG_SUFFIX = " />"; // 后缀

    private static final String OVERRIDE_IMG_PREFIX = "<img src=\""; // 重写的图片前缀
    private static final String OVERRIDE_IMG_SUFFIX = "\" />"; // 后缀

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
    public static void replaceImg(File file, Img[] imgs) {
        // 才没有在骂人
        StringBuilder sb = readHtml(file);
        // 图片索引
        int index = 0;

        for (int i = 0; i < imgs.length; i++) {
            // 插入点
            int insertPoint = sb.indexOf(IMG_PREFIX, index);
            // 删除点
            int deleteEndPoint = sb.indexOf(IMG_SUFFIX, insertPoint) + IMG_SUFFIX.length();
            // 删除
            sb.delete(insertPoint, deleteEndPoint);
            // 插入
            String insertStr = OVERRIDE_IMG_PREFIX + imgs[index++].getUrl() + OVERRIDE_IMG_SUFFIX;
            sb.insert(insertPoint, insertStr);
        }

        overHtml(file, sb.toString());

        System.out.println(sb);
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

    // 转为html，复制的
    public static void docxToHtml(String path, String toPath) throws Docx4JException, IOException {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(path));
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
