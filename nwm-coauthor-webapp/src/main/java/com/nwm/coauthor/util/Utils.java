package com.nwm.coauthor.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static String encrypt(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte byteData[] = md.digest(str.getBytes("UTF-8"));
        
        return new String(byteData);
    }    
}
