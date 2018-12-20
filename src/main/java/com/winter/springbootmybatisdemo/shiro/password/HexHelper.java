package com.winter.springbootmybatisdemo.shiro.password;

/**
 * 16进制辅助类
 */
public class HexHelper {


    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * 二进制转换成char类型
     * @param data
     * @return
     */
    public static char[] encode(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 二进制转16进制
     * @param bytes
     * @return
     */
    public static String encodeToString(byte[] bytes) {
        char[] encodedChars = encode(bytes);
        return new String(encodedChars);
    }



}
