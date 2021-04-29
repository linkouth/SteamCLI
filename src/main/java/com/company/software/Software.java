package com.company.software;

import com.company.category.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Software {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private int price;

  @ManyToMany(targetEntity = Category.class)
  private List<Category> categories;

  public Software() {}

  public Software(String name, int price) {
    this.name = name;
    this.price = price;
  }
}
