package cn.sdadgz.dhc_springboot.Utils;

import cn.sdadgz.dhc_springboot.mapper.FirstTitleMapper;
import cn.sdadgz.dhc_springboot.mapper.SecondTitleMapper;
import cn.sdadgz.dhc_springboot.service.IFirstTitleService;
import cn.sdadgz.dhc_springboot.service.ISecondTitleService;
import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class GeneralUtil {

    // 他自己
    private static GeneralUtil generalUtil;

    // 构造前
    @PostConstruct
    private void init() {
        generalUtil = this;
    }

    @Resource
    @TableField(exist = false)
    private IFirstTitleService firstTitleService;

    @Resource
    @TableField(exist = false)
    private ISecondTitleService secondTitleService;

    @Resource
    @TableField(exist = false)
    private FirstTitleMapper firstTitleMapper;

    @Resource
    @TableField(exist = false)
    private SecondTitleMapper secondTitleMapper;

    // 非空
    public static boolean isNull(Object obj) {
        // 空指针否
        if (obj == null) {
            return true;
        }
        // 空字符串否
        if (obj instanceof String) {
            String str = (String) obj;
            return "".equals(str);
        }
        // 为0的数字
        if (obj instanceof Integer) {
            Integer in = (Integer) obj;
            return in.equals(0);
        }
        return false;
    }

    public static IFirstTitleService getFirstTitleService() {
        return generalUtil.firstTitleService;
    }

    public static ISecondTitleService getSecondTitleService() {
        return generalUtil.secondTitleService;
    }

    public static FirstTitleMapper getFirstTitleMapper() {
        return generalUtil.firstTitleMapper;
    }

    public static SecondTitleMapper getSecondTitleMapper() {
        return generalUtil.secondTitleMapper;
    }
}
