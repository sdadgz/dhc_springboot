package cn.sdadgz.dhc_springboot.Utils;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

public class UserUtil {

    public final static String SALT = "我真是个活废物，除了复制别人的代码还是复制别人的代码，这就是低级码农吗";

    // 加密
    public static String getPassword(String password) throws NoSuchAlgorithmException {
        return Md5Util.md5(password + SALT);
    }

}
