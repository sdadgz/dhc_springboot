package cn.sdadgz.dhc_springboot.Utils;

import java.security.NoSuchAlgorithmException;

import static cn.sdadgz.dhc_springboot.Utils.MagicValueUtil.SALT;

public class UserUtil {

    // 加密
    public static String getPassword(String password) throws NoSuchAlgorithmException {
        return Md5Util.md5(password + SALT);
    }

}
