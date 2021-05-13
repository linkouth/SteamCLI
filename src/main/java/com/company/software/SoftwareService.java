package com.company.software;

import com.company.category.Category;

import java.util.List;
import java.util.Optional;

public class SoftwareService {
  private final SoftwareDao softwareDao = new SoftwareDao();

  public void createSoftware(Software software) {
    softwareDao.save(software);
  }

  public Optional<Software> findById(int id) {
    return Optional.ofNullable(softwareDao.findById(id));
  }

  public Optional<List<Software>> findAll() { return Optional.ofNullable(softwareDao.findAll()); }

  public void update(Software software) { softwareDao.update(software); }

  public void delete(Software software) {
    softwareDao.delete(software);
  }
}
