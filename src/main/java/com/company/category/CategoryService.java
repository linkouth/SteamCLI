package com.company.category;

import java.util.List;
import java.util.Optional;

public class CategoryService {
  private final CategoryDao categoryDao = new CategoryDao();

  public void createCategory(String name) {
    Category category = new Category(name);
    categoryDao.save(category);
  }

  public Optional<Category> findById(int id) {
    return Optional.ofNullable(categoryDao.findById(id));
  }

  public Optional<List<Category>> findAll() { return Optional.ofNullable(categoryDao.findAll()); }

  public void update(Category category) { categoryDao.update(category); }

  public void delete(Category category) {
    categoryDao.delete(category);
  }
}
