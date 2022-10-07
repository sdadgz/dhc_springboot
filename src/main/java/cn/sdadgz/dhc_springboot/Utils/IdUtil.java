package cn.sdadgz.dhc_springboot.Utils;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class IdUtil {

    // uuid
    public static String uuid() throws NoSuchAlgorithmException {
        return uuid(MagicValueUtil.SALT);
    }

    // uuid加盐重写
    public static String uuid(String salt) throws NoSuchAlgorithmException {
        // 时间
        long currentTimeMillis = System.currentTimeMillis();
        String time = String.valueOf(currentTimeMillis);
        // 随机数
        Random random = new Random();
        long l = random.nextLong();
        String rand = String.valueOf(l);

        return Md5Util.md5(time + rand + salt);
    }

    // 获取userId
    public static int getId(HttpServletRequest request) {
        return getId(request.getHeader("token"));
    }

    // 获取userId
    public static int getId(String token) {
        return Integer.parseInt(JwtUtil.getAudience(token));
    }

    // 获取username
    public static String getName(HttpServletRequest request) {
        return getName(request.getHeader("token"));
    }

    // 获取username
    public static String getName(String token) {
        return JwtUtil.getClaimByName(token, "username").asString();
    }

}
