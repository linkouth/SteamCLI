package com.company.user;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.Instant;

import com.company.address.Address;
import com.company.util.HashUtil;

@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;

  private int age;

  private String password;

  private String salt;

  @ManyToOne(targetEntity = Address.class)
  private Address address;

  public User() {}

  public User(String name, int age, String password) {
    this.name = name;
    this.age = age;
    this.setPassword(password);
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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    String salt = String.valueOf(Timestamp.from(Instant.now()).getTime());
    this.setSalt(salt);
    this.password = HashUtil.hashPassword(password, salt);
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) { this.salt = salt; }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}
