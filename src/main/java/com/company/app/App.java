package com.company.app;

import com.company.user.AuthService;

import java.io.PrintStream;
import java.util.Scanner;

interface AppInterface {
  void parseCommand();
  Scanner in = new Scanner(System.in);
  PrintStream out = System.out;
}

public class App implements AppInterface {
  public static final App INSTANCE = new App();

  private final AuthService authService = new AuthService();

  private final UserApp userApp = new UserApp();
  private final SoftwareApp softwareApp = new SoftwareApp();
  private final GameApp gameApp = new GameApp();
  private final CategoryApp categoryApp = new CategoryApp();

  private int page = 0;

  public void run() {
    while(true) {
      parseCommand();
    }
  }

  public void parseCommand() {
    switch (page) {
      case 0 -> baseParseCommand();
      case 1 -> userApp.parseCommand();
      case 2 -> softwareApp.parseCommand();
      case 3 -> gameApp.parseCommand();
      case 4 -> categoryApp.parseCommand();
    }
  }

  void baseParseCommand() {
    if (authService.isAuth()) {
      parseAuthUser();
    } else {
      parseNotAuthUser();
    }
  }

  void parseNotAuthUser() {
    out.println("1 – логин");
    out.println("2 – заргистрироваться");
    out.println("0 – завершить работу");
    out.println("Введите команду: ");

    try {
      int commandNumber = Integer.parseInt(in.nextLine());
      switch (commandNumber) {
        case 1 -> login();
        case 2 -> singUp();
        case 3 -> logout();
        case 0 -> System.exit(0);
        default -> out.println("Введите корректную команду");
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void parseAuthUser() {
    out.println("1 – перейти на страницу с пользователями");
    out.println("2 – перейти на страницу с программами");
    out.println("3 – перейти на страницу с играми");
    out.println("4 – перейти на страницу с категориями");
    out.println("5 – вывести список приобретенных игр/программ");
    out.println("Введите команду: ");

    try {
      int commandNumber = Integer.parseInt(in.nextLine());
      switch (commandNumber) {
        case 1 -> goToUserApp();
        case 2 -> goToSoftwareApp();
        case 3 -> goToGameApp();
        case 4 -> goToCategoryApp();
        case 0 -> System.exit(0);
        default -> out.println("Введите корректную команду");
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void goToMain() {
    page = 0;
  }

  void goToUserApp() {
    page = 1;
  }

  void goToSoftwareApp() {
    page = 2;
  }

  void goToGameApp() {
    page = 3;
  }

  void goToCategoryApp() {
    page = 4;
  }

  void login() {
    try {
      out.println("Введите ваш id: ");
      int id = Integer.parseInt(in.nextLine());

      out.println("Введите пароль: ");
      String password = in.nextLine();

      authService.login(id, password);
    } catch (NumberFormatException err) {
      out.println("Некорректный id или пароль");
    }
  }

  void singUp() {
    try {
      out.println("Введите имя: ");
      String name = in.nextLine();

      out.println("Введите возраст: ");
      int age = Integer.parseInt(in.nextLine());

      out.println("Введите пароль: ");
      String password = in.nextLine();

      authService.signup(name, age, password);
    } catch (NumberFormatException err) {
      out.println("Некорректный id или пароль");
    }
  }

  void logout() {
    authService.logout();
    out.println("Вы вышли из учетной записи");
  }
}
