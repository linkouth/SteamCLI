package com.company.app;

import com.company.user.User;
import com.company.user.UserService;

import java.util.List;
import java.util.Optional;

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
      users.get().forEach(out::println);
    }
  }

  void printUserById() {
    try {
      out.println("Введите id:");
      int id = Integer.parseInt(in.nextLine());
      Optional<User> user = userService.findById(id);

      if (user.isEmpty()) {
        out.println("Пользователя с id " + id + " не существует");
      } else {
        out.println("Пользователь с id: " + id);
        out.println(user.get());
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void createUser() {
    try {
      out.println("Введите возраст:");
      int age = Integer.parseInt(in.nextLine());
      out.println("Введите имя:");
      String name = in.nextLine();
      out.println("Введите пароль:");
      String password = in.nextLine();

      userService.createUser(name, age, password);
      out.println("Создан новый пользователь");
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void editUser() {
    try {
      out.println("Введите id:");
      int id = Integer.parseInt(in.nextLine());
      Optional<User> result = userService.findById(id);

      if (result.isEmpty()) {
        out.println("Пользователь с id " + id + " не существует");
        out.println();
        return;
      }

      User user = result.get();

      out.println("Введите возраст:");
      int age = Integer.parseInt(in.nextLine());
      out.println("Введите имя:");
      String name = in.nextLine();
      out.println("Введите пароль:");
      String password = in.nextLine();

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
      out.println("Введите id:");
      int id = Integer.parseInt(in.nextLine());
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
