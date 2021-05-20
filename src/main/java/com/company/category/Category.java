package com.company.category;

import com.company.software.Software;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;

  @ManyToMany(targetEntity = Software.class)
  private List<Software> softwares;

  public Category(String name) {
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

  public List<Software> getSoftwares() {
    return softwares;
  }

  public void setSoftwares(List<Software> softwares) {
    this.softwares = softwares;
  }

  public void addSoftware(Software software) {
    this.softwares.add(software);
  }
}
