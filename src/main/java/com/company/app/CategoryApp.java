package com.company.app;

import com.company.category.Category;
import com.company.category.CategoryService;
import com.company.util.Validations;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

public class CategoryApp implements AppInterface {
  private final CategoryService categoryService = new CategoryService();

  public void parseCommand() {
    printHelp();

    try {
      int commandNumber = Integer.parseInt(in.nextLine());
      switch (commandNumber) {
        case 1 -> printAllCategories();
        case 2 -> printCategoryById();
        case 3 -> createCategory();
        case 4 -> editCategory();
        case 5 -> deleteCategory();
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
    out.println("3 – добавить новую категорию");
    out.println("4 – изменить существующую категорию");
    out.println("5 – удалить существующую категорию");
    out.println("0 – вернуться на главную");
    out.println("Введите команду: ");
  }

  void printAllCategories() {
    Optional<List<Category>> categories = categoryService.findAll();

    if (categories.isEmpty() || categories.get().isEmpty()) {
      out.println("Список категорий пуст.");
    } else {
      out.println("Список категорий:");
      try {
        List<Category> items = categories.get();
        for (Category category: items) {
          out.println(serializer.writeValueAsString(category));
        }
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }

  void printCategoryById() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Category> category = categoryService.findById(id);

      if (category.isEmpty()) {
        out.println("Категория с id " + id + " не существует");
      } else {
        out.println("Категория с id: " + id);
        try {
          out.println(serializer.writeValueAsString(category.get()));
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void createCategory() {
    try {
      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите название:");
        name = in.nextLine();
        if (!Validations.isValidName(name)) {
          out.println("Название должно быть не пустым");
        }
      }

      categoryService.createCategory(name);
      out.println("Добавлена новая категория");
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void editCategory() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Category> result = categoryService.findById(id);

      if (result.isEmpty()) {
        out.println("Категория с id " + id + " не существует");
        out.println();
        return;
      }

      Category category = result.get();

      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите название категории:");
        name = in.nextLine();
        if (!Validations.isValidName(name)) {
          out.println("Название должно быть не пустым");
        }
      }

      category.setName(name);

      categoryService.update(category);

      out.println("Категория с id +" + id + " изменена");
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void deleteCategory() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Category> category = categoryService.findById(id);

      if (category.isEmpty()) {
        out.println("Категория с id " + id + " не существует");
      } else {
        categoryService.delete(category.get());
        out.println("Категория с id: " + id + " удалена");
        out.println(category.get());
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }
}
