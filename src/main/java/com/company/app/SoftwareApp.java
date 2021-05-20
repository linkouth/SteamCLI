package com.company.app;

import com.company.category.Category;
import com.company.category.CategoryService;
import com.company.software.Software;
import com.company.software.SoftwareService;
import com.company.util.Validations;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SoftwareApp implements AppInterface {
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
    Optional<List<Software>> softwares = softwareService.findAll();

    if (softwares.isEmpty() || softwares.get().isEmpty()) {
      out.println("Список программ пуст.");
    } else {
      out.println("Список программ:");
      try {
        List<Software> items = softwares.get();
        for (Software software: items) {
          out.println(serializer.writeValueAsString(software));
        }
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
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
        try {
          out.println(serializer.writeValueAsString(user.get()));
        }  catch (JsonProcessingException e) {
          e.printStackTrace();
        }
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
        if (!Validations.isValidName(name)) {
          out.println("Имя должно быть не пустым");
        }
      }

      int price = -1;
      while (!Validations.isValidPrice(price)) {
        out.println("Введите цену:");
        price = Integer.parseInt(in.nextLine());
        if (!Validations.isValidPrice(price)) {
          out.println("Цена должен больше нуля и не быть пустым");
        }
      }

      int categoryId = -1;
      List<Category> categories = new ArrayList<>();
      boolean isValidIds = false;
      while (!isValidIds) {
        out.println("Введите id категории через пробел:");
        List<Integer> categoryIds = Arrays
                .stream(in.nextLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Optional<Category>> items = categoryIds.stream()
                .map(categoryService::findById)
                .collect(Collectors.toList());
        if (!items.stream().allMatch(Optional::isPresent)) {
          out.println("Id должны существовать");
        } else {
          isValidIds = true;
          categories = items.stream().map(Optional::get).collect(Collectors.toList());
        }
      }

      Software software = new Software(name, price, categories);
      if (!categories.isEmpty()) {
        List<Category> categoriesFromDB = new ArrayList<>(categories);
        for (int i = 0; i < categoriesFromDB.size(); ++i) {
          Category category = categoriesFromDB.get(i);
          category.addSoftware(software);
          software.addCategory(category);
        }
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
