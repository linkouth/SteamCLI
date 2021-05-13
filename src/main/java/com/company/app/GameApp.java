package com.company.app;

import com.company.game.Game;
import com.company.game.GameService;
import com.company.util.Validations;

import java.util.List;
import java.util.Optional;


public class GameApp implements AppInterface {
  private final GameService gameService = new GameService();

  public void parseCommand() {
    printHelp();

    try {
      int commandNumber = Integer.parseInt(in.nextLine());
      switch (commandNumber) {
        case 1 -> printAllGames();
        case 2 -> printGameById();
        case 3 -> createGame();
        case 4 -> editGame();
        case 5 -> deleteGame();
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
    out.println("3 – добавить новую игру");
    out.println("4 – изменить существующую игру");
    out.println("5 – удалить существующую игру");
    out.println("0 – вернуться на главную");
    out.println("Введите команду: ");
  }

  void printAllGames() {
    Optional<List<Game>> games = gameService.findAll();

    if (games.isEmpty() || games.get().isEmpty()) {
      out.println("Список игр пуст.");
    } else {
      out.println("Список игр:");
      games.get().stream().map(gson::toJson).forEach(out::println);
    }
  }

  void printGameById() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Game> game = gameService.findById(id);

      if (game.isEmpty()) {
        out.println("Игра с id " + id + " не существует");
      } else {
        out.println("Игра с id: " + id);
        out.println(gson.toJson(game.get()));
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void createGame() {
    try {
      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите название:");
        name = in.nextLine();
        if (!Validations.isValidName(name)) {
          out.println("Название должно быть не пустым");
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

      int hasMultiplayer = -1;
      while (!Validations.isValidHasMultiplayer(hasMultiplayer)) {
        out.println("Есть мультиплеер (да-1 / нет-0):");
        hasMultiplayer = Integer.parseInt(in.nextLine());
        if (!Validations.isValidHasMultiplayer(hasMultiplayer)) {
          out.println("Некорректный ввод");
        }
      }

      gameService.createGame(name, price, hasMultiplayer == 1);
      out.println("Добавлена новая игра");
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void editGame() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Game> result = gameService.findById(id);

      if (result.isEmpty()) {
        out.println("Программа с id " + id + " не существует");
        out.println();
        return;
      }

      Game game = result.get();

      String name = null;
      while (!Validations.isValidName(name)) {
        out.println("Введите название игры:");
        name = in.nextLine();
        if (!Validations.isValidName(name)) {
          out.println("Название должно быть не пустым");
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

      game.setName(name);
      game.setPrice(price);

      gameService.update(game);

      out.println("Игра с id +" + id + " изменена");
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }

  void deleteGame() {
    try {
      int id = -1;
      while (!Validations.isValidId(id)) {
        out.println("Введите id:");
        id = Integer.parseInt(in.nextLine());
        if (!Validations.isValidId(id)) {
          out.println("id должно быть не пустым и больше нуля");
        }
      }
      Optional<Game> game = gameService.findById(id);

      if (game.isEmpty()) {
        out.println("Игра с id " + id + " не существует");
      } else {
        gameService.delete(game.get());
        out.println("Игра с id: " + id + " удалена");
        out.println(game.get());
      }
      out.println();
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }
}
