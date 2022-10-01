package cn.sdadgz.dhc_springboot.Utils;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

import static cn.sdadgz.dhc_springboot.Utils.StringUtil.SALT;

public class UserUtil {

    // 加密
    public static String getPassword(String password) throws NoSuchAlgorithmException {
        return Md5Util.md5(password + SALT);
    }

}
