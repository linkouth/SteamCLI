package com.company.user;

import com.company.util.HashUtil;

import java.util.Optional;

public class AuthService {
  private User currentUser;

  private final UserService userService = new UserService();

  public void login(int id, String password) {
    Optional<User> result = userService.findById(id);
    if (result.isEmpty()) {
      System.out.println("Incorrect login/password");
      return;
    }
    User user = result.get();

    String hashedPassword = HashUtil.hashPassword(password, user.getSalt());
    if (user.getPassword().equals(hashedPassword)) {
      currentUser = user;
      System.out.println("Successful login");
    } else {
      System.out.println("Failed login");
    }
  }

  public void logout() {
    currentUser = null;
  }

  public void signup(String name, int age, String password) {
    // TODO replace with functional objects
    // see https://en.wikipedia.org/wiki/Function_object
    if (name.isEmpty()) {
      System.out.println("Имя не должно быть пустым");
      return;
    }
    if (age < 12) {
      System.out.println("Имя не должно быть пустым");
      return;
    }
    if (password.length() < 5) {
      System.out.println("Пароль должен быть длинее 4 символов");
      return;
    }

    userService.createUser(name, age, password);
  }

  public Boolean isAuth() {
    return currentUser != null;
  }
}
