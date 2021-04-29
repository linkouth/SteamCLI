package com.company.app;

import com.company.game.Game;
import com.company.game.GameService;

public class GameApp implements AppInterface {
  private final GameService gameService = new GameService();

  @Override
  public void parseCommand() {

  }

  void createNewGame() {
    out.println("Введите название игры: ");
    String name = in.nextLine();
    try {
      out.println("Введите цену игры: ");
      int price = Integer.parseInt(in.nextLine());
      Game game = new Game(name, price, false);

      gameService.createGame(game);
    } catch (NumberFormatException err) {
      out.println("Некорректный ввод");
    }
  }
}
