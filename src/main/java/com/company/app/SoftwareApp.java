package com.company.app;

import com.company.category.Category;
import com.company.category.CategoryService;
import com.company.software.Software;
import com.company.software.SoftwareService;
import com.company.util.Validations;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoftwareApp implements AppInterface {
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private final SoftwareService softwareService = new SoftwareService();
  private final CategoryService categoryService = new CategoryService();

  public void parseCommand() {
    printHelp();

    try {
      int commandNumber = Integer.parseInt(in.nextLine());
      switch (commandNumber) {
        case 1 -> printAllSoftwares();
        case 2 -> printSoftwareById();
        case 3 -> createSoftware();
        case 4 -> editSoftware();
        case 5 -> deleteSoftware();
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
    out.println("3 – добавить новую программу");
    out.println("4 – изменить существующую программу");
    out.println("5 – удалить существующую программ");
    out.println("0 – вернуться на главную");
    out.println("Введите команду: ");
  }

  void printAllSoftwares() {
    Optional<List<Software>> users = softwareService.findAll();

    if (users.isEmpty() || users.get().isEmpty()) {
      out.println("Список программ пуст.");
    } else {
      out.println("Список программ:");
      users.get().forEach(out::println);
    }
  }

  void printSoftwareById() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Software> user = softwareService.findById(id);

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

  void createSoftware() {
    try {
      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите имя:");
        name = in.nextLine();
        out.println("Имя должно быть не пустым");
      }

      int price = -1;
      while (!Validations.isValidPrice(price)) {
        out.println("Введите цену:");
        price = Integer.parseInt(in.nextLine());
        out.println("Цена должен больше нуля и не быть пустым");
      }

      int categoryId = -1;
      Optional<Category> category = Optional.empty();
      while (!Validations.isValidId(categoryId) && category.isEmpty()) {
        out.println("Введите id категории:");
        categoryId = Integer.parseInt(in.nextLine());
        category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
          out.println("Id должно быть больше нуля и существовать");
        }
      }

      ArrayList<Category> categories = new ArrayList<>();
      Software software = new Software(name, price, categories);
      if (category.isPresent()) {
        categories.add(category.get());
        category.get().addSoftware(software);
      }
      softwareService.createSoftware(software);
      out.println("Добавлена новая программа");
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void editSoftware() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Software> result = softwareService.findById(id);

      if (result.isEmpty()) {
        out.println("Программа с id " + id + " не существует");
        out.println();
        return;
      }

      Software software = result.get();

      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите название ПО:");
        name = in.nextLine();
        out.println("Имя должно быть не пустым");
      }

      int price = -1;
      while (!Validations.isValidPrice(price)) {
        out.println("Введите цену:");
        price = Integer.parseInt(in.nextLine());
        out.println("Цена должен больше нуля и не быть пустым");
      }

      int categoryId = -1;
      Optional<Category> category = Optional.empty();
      while (!Validations.isValidId(categoryId) && category.isEmpty()) {
        out.println("Введите id категории:");
        categoryId = Integer.parseInt(in.nextLine());
        category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
          out.println("Id должно быть больше нуля и существовать");
        }
      }

      category.ifPresent(software::addCategory);
      software.setName(name);
      software.setPrice(price);

      softwareService.update(software);

      out.println("Программа с id +" + id + " изменена");
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void deleteSoftware() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Software> software = softwareService.findById(id);

      if (software.isEmpty()) {
        out.println("Программа с id " + id + " не существует");
      } else {
        softwareService.delete(software.get());
        out.println("Программа с id: " + id + " удален");
        out.println(software.get());
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }
}
