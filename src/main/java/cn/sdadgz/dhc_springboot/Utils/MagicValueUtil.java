package cn.sdadgz.dhc_springboot.Utils;

public class MagicValueUtil {

    public static final String LEVER = "/";
    public static final String UNDEFINED = "未定义";
    public static final String UNDERLINE = "_";
    public static final String SPLIT = " ";
    public static final String EMPTY_STRING = "";
    public static final String RESULT_LISTS = "lists";
    public static final String RESULT_TOTAL = "total";
    public static final String RESULT_TEXT = "text";
    public static final String RESULT_ESSAY = "essay";
    public static final String RESULT_STATUS = "status";
    public static final String REQUEST_LISTS = "idList";

    public static final String[] ESSAY_SUPPORT_TYPE = {".docx"}; // 支持的文件类型
    public static final String HTML_SUFFIX = ".html"; // html文件后缀

    public static final String IMG_PREFIX = "<img "; // 修改前的供查找的图片前缀
    public static final String IMG_SUFFIX = " />"; // 后缀

    public static final String OVERRIDE_IMG_PREFIX = "<img src=\""; // 重写的图片前缀
    public static final String OVERRIDE_IMG_SUFFIX = "\" />"; // 后缀

    public final static String SALT = "我真是个活废物，除了复制别人的代码还是复制别人的代码，这就是低级码农吗";

    public static final int DEFAULT_ESSAY_ID = 0; // 图片上传默认essayId，该id下图片去重
    public static final String[] IMG_SUPPORT_TYPE = {".jpg", ".jpeg", ".png"};
    public static final int REDUCE_X = 700 * 2;
    public static final int REDUCE_Y = 394 * 2;

}
