package com.company.user;

import com.company.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class UserDao {
  User findById(int id) {
    return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
  }

  void save(User user) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.save(user);
    tx1.commit();
    session.close();
  }

  void update(User user) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.update(user);
    tx1.commit();
    session.close();
  }

  void delete(User user) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.delete(user);
    tx1.commit();
    session.close();
  }

  ArrayList<User> findAll() {
    return (ArrayList<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
  }
}
