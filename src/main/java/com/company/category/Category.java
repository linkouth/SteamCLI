package com.company.category;

import com.company.software.Software;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;

  @ManyToMany(targetEntity = Software.class)
  private List<Software> softwares;

  public Category(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Category() {}

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
}
