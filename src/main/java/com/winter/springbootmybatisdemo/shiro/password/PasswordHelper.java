package com.winter.springbootmybatisdemo.shiro.password;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 密码辅助类
 */
public class PasswordHelper {
    /** 摘要算法 */
    private static final String ALGORITHM = "SHA-512";

    private static final RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    /**
     * 加密密码
     * @param password 密码
     * @param salt  盐
     * @return
     */
    public static String encodePassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
        byte[] saltByte = salt.getBytes();
        byte[] passwordByte = password.getBytes();
        digest.reset();
        digest.update(saltByte);
        byte[] passwordDigest = digest.digest(passwordByte);
        for (int i = 0; i < 3; i++){
            digest.reset();
            passwordDigest = digest.digest(passwordDigest);
        }
        return HexHelper.encodeToString(passwordDigest);
    }

    /**
     * 检查密码是否相等
     * @param encryptPassword 加密后的密码
     * @param originPassword 原密码
     * @param salt 盐
     * @return
     */
    public static boolean checkPassword(String encryptPassword, String originPassword, String salt) throws NoSuchAlgorithmException{

        String origin = encodePassword(originPassword, salt);
        return Objects.equals(encryptPassword, origin);
    }

    /**
     * 生成一个随机盐
     * @return
     */
    public static String generatorRandomNumber(){
        return randomNumberGenerator.nextBytes().toHex().toUpperCase();
    }


}
