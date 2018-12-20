package com.winter.springbootmybatisdemo.shiro.password;

import java.security.NoSuchAlgorithmException;

public class test {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String pass = PasswordHelper.encodePassword("1234","D62F05368E98C113CDB24AACF641E770");
        if(pass.equals("502285AB96A2E1455FE6A3F8BC5D929E3C53DA03232467B65B93A84E09818C8A5B6DA772E49A6B34E73EED47ADD12DBD6971A6C763D6F2C629EA4283BEB522CC")){
            System.out.println("相同");
        }else{
            System.out.println("不相同");
        }
    }
}
