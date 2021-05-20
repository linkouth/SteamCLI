package com.company.app;

import com.company.user.User;
import com.company.user.UserService;
import com.company.util.Validations;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;

public class UserApp implements AppInterface {
  private final UserService userService = new UserService();

  @Override
  public void parseCommand() {
    printHelp();

    try {
      int commandNumber = Integer.parseInt(in.nextLine());
      switch (commandNumber) {
        case 1 -> printAllUsers();
        case 2 -> printUserById();
        case 3 -> createUser();
        case 4 -> editUser();
        case 5 -> deleteUser();
        case 0 -> App.INSTANCE.goToMain();
        default -> out.println("Введите корректную команду");
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    } catch (Exception e) {
      out.println("Ошибка: " + e);
    }
  }

  void printHelp() {
    out.println("1 – вывести список");
    out.println("2 – вывести по id");
    out.println("3 – создать нового пользователя");
    out.println("4 – изменить существующего пользователя");
    out.println("5 – удалить существующего пользователя");
    out.println("0 – вернуться на главную");
    out.println("Введите команду: ");
  }

  void printAllUsers() {
    Optional<List<User>> users = userService.findAll();

    if (users.isEmpty() || users.get().isEmpty()) {
      out.println("Список пользователей пуст.");
    } else {
      out.println("Список пользователей:");
      try {
        List<User> items = users.get();
        for (User user: items) {
          out.println(serializer.writeValueAsString(user));
        }
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }

  void printUserById() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<User> user = userService.findById(id);

      if (user.isEmpty()) {
        out.println("Пользователя с id " + id + " не существует");
      } else {
        try {
          out.println("Пользователь с id: " + id);
          out.println(serializer.writeValueAsString(user));
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void createUser() {
    try {

      int age = -1;
      while (!Validations.isValidAge(age)) {
        out.println("Введите возраст:");
        age = Integer.parseInt(in.nextLine());
        out.println("Возраст должен быть не пустым и больше нуля");
      }

      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите имя:");
        name = in.nextLine();
        out.println("Имя должно быть не пустым");
      }

      String password = null;
      while (!Validations.isValidPassword(password)) {
        out.println("Введите пароль:");
        password = in.nextLine();
        out.println("Пароль должен быть не пустым и иметь не менее 5 символов");
      }

      userService.createUser(name, age, password);
      out.println("Создан новый пользователь");
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void editUser() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<User> result = userService.findById(id);

      if (result.isEmpty()) {
        out.println("Пользователь с id " + id + " не существует");
        out.println();
        return;
      }

      User user = result.get();

      int age = -1;
      while (!Validations.isValidAge(age)) {
        out.println("Введите возраст:");
        age = Integer.parseInt(in.nextLine());
        if (!Validations.isValidAge(age)) {
          out.println("Возраст должен быть не пустым и больше нуля");
        }
      }

      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите имя:");
        name = in.nextLine();
        out.println("Имя должно быть не пустым");
      }

      String password = null;
      while (!Validations.isValidPassword(password)) {
        out.println("Введите пароль:");
        password = in.nextLine();
        if (!Validations.isValidPassword(password)) {
          out.println("Пароль должен быть не пустым и иметь не менее 5 символов");
        }
      }

      user.setAge(age);
      user.setName(name);
      user.setPassword(password);

      userService.update(user);

      out.println("Пользователь с id +" + id + " изменен");
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void deleteUser() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<User> user = userService.findById(id);

      if (user.isEmpty()) {
        out.println("Пользователя с id " + id + " не существует");
      } else {
        userService.delete(user.get());
        out.println("Пользователь с id: " + id + " удален");
        out.println(user.get());
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }
}
