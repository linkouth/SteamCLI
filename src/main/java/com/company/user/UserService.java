package com.company.user;

import com.company.util.HashUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class UserService {
  private final UserDao userDao = new UserDao();

  public void createUser(String name, int age, String password) {
    User user = new User(name, age, password);
    userDao.save(user);
  }

  public Optional<User> findById(int id) {
    return Optional.ofNullable(userDao.findById(id));
  }

  public Optional<List<User>> findAll() { return Optional.ofNullable(userDao.findAll()); }

  public void update(User user) { userDao.update(user); }

  public void delete(User user) {
    userDao.delete(user);
  }
}
