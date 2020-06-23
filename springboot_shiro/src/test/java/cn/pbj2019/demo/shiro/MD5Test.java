package cn.pbj2019.demo.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @ClassName: MD5Test
 * @Author: pbj
 * @Date: 2019/9/4 11:13
 * @Description: TODO
 */
public class MD5Test {
    /*
     * base64加密
     * */
    public static String encodeBase64(String str) {
        return Base64.encodeToString(str.getBytes());
    }

    /*
     * base64解密
     * */

    public static String decodeBase64(String str) {
        return Base64.decodeToString(str);
    }

    /*
     * Md5加密，shiro框架中自带Md5，Md5没有解密
     * */
    public static String md5(String str, String salt) {
        return new Md5Hash(str, salt).toString();
    }

    public static void main(String[] args) {

        SimpleHash simpleHash = new SimpleHash("md5", "13200000004@Bbx_888888");

        String passwordMd5 = simpleHash.toString();

        System.out.println("Md5加密:" + passwordMd5.equals("34e0e688ab2aabc98ff876efd50bb11f"));
    }
}
