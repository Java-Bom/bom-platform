package com.javabom.bomplatform.utils;

import lombok.experimental.UtilityClass;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class ShaUtils {
    public static String sha1(String value) {
        String sha1 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(value.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            StringBuffer hashtext = new StringBuffer(no.toString(16));

            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1;
    }


}
