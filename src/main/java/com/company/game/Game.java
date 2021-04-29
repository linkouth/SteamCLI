package com.company.game;

import com.company.software.Software;

import javax.persistence.*;

@Entity
public class Game extends Software {
  private Boolean hasMutliplayer;

  public Game() {}

  public Game(String name, int price, Boolean hasMutliplayer) {
    super(name, price);
    this.hasMutliplayer = hasMutliplayer;
  }
  public Boolean getHasMutliplayer() {
    return hasMutliplayer;
  }

  public void setHasMutliplayer(Boolean hasMutliplayer) {
    this.hasMutliplayer = hasMutliplayer;
  }
}
