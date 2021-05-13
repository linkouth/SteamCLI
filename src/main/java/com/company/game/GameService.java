package com.company.game;

import com.company.category.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameService {
  private final GameDao gameDao = new GameDao();

  public void createGame(String name, int price, boolean hasMultiplayer) {
    Game game = new Game(name, price, new ArrayList<>(), hasMultiplayer);
    gameDao.save(game);
  }

  public Optional<Game> findById(int id) {
    return Optional.ofNullable(gameDao.findById(id));
  }

  public Optional<List<Game>> findAll() { return Optional.ofNullable(gameDao.findAll()); }

  public void update(Game game) { gameDao.update(game); }

  public void delete(Game game) {
    gameDao.delete(game);
  }
}
