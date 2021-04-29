package com.company.game;

import com.company.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class GameDao {
  Game findById(int id) {
    return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Game.class, id);
  }

  void save(Game user) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.save(user);
    tx1.commit();
    session.close();
  }

  void update(Game user) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.update(user);
    tx1.commit();
    session.close();
  }

  void delete(Game user) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.delete(user);
    tx1.commit();
    session.close();
  }

  ArrayList<Game> findAll() {
    return (ArrayList<Game>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Game").list();
  }
}
