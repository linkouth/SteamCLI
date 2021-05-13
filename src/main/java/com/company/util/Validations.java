package com.company.util;

import java.util.Locale;

public class Validations {
  public static boolean isValidId(int id) {
    return id > 0;
  }

  public static boolean isValidAge(int age) {
    return age > 0;
  }

  public static boolean isValidName(String name) {
    return name != null && !name.isBlank();
  }

  public static boolean isValidPassword(String password) {
    return password != null && !password.isBlank() && password.length() >= 5;
  }

  public static boolean isValidPrice(int price) {
    return price > 0;
  }

  public static boolean isValidHasMultiplayer(int code) {
    return code == 0 || code == 1;
  }
}
