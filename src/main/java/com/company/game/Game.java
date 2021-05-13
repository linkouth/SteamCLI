package com.company.game;

import com.company.category.Category;
import com.company.software.Software;

import javax.persistence.*;
import java.util.List;

import com.google.gson.annotations.Expose;

@Entity
public class Game extends Software {
  @Expose
  private Boolean hasMutliplayer;

  public Game() {}

  public Game(String name, int price, List<Category> categories, Boolean hasMutliplayer) {
    super(name, price, categories);
    this.hasMutliplayer = hasMutliplayer;
  }
  public Boolean getHasMutliplayer() {
    return hasMutliplayer;
  }

  public void setHasMutliplayer(Boolean hasMutliplayer) {
    this.hasMutliplayer = hasMutliplayer;
  }
}
