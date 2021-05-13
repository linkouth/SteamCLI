package com.company.software;

import com.company.category.Category;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Software {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Expose
  private int id;
  @Expose
  private String name;
  @Expose
  private int price;

  @ManyToMany(targetEntity = Category.class, mappedBy = "softwares")
  private List<Category> categories;

  public Software() {}

  public Software(String name, int price, List<Category> categories) {
    this.name = name;
    this.price = price;
    this.categories = categories;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public void addCategory(Category category) { this.categories.add(category); }
}
