package com.company.category;

import com.company.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class CategoryDao {
  Category findById(int id) {
    return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Category.class, id);
  }

  void save(Category category) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.save(category);
    tx1.commit();
    session.close();
  }

  void update(Category category) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.merge(category);
    tx1.commit();
    session.close();
  }

  void delete(Category category) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.delete(category);
    tx1.commit();
    session.close();
  }

  ArrayList<Category> findAll() {
    return (ArrayList<Category>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Category").list();
  }
}
