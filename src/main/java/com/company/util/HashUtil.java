package com.company.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class HashUtil {
  public static String hashPassword(String password, String salt) {
    try {
      KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] hash = factory.generateSecret(spec).getEncoded();

      return Base64.getEncoder().encodeToString(hash);
    } catch (Exception e) {
      return "";
    }
  }
}
