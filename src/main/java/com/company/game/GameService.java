package com.company.game;

import java.util.ArrayList;

public class GameService {
  private final GameDao gameDao = new GameDao();

  public ArrayList<Game> retrieveGames() {
    return gameDao.findAll();
  }

  public void createGame(Game game) {
    gameDao.save(game);
  }
}
