package com.company.software;

import com.company.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class SoftwareDao {
  Software findById(int id) {
    return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Software.class, id);
  }

  void save(Software software) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.save(software);
    tx1.commit();
    session.close();
  }

  void update(Software Software) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.update(Software);
    tx1.commit();
    session.close();
  }

  void delete(Software Software) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.delete(Software);
    tx1.commit();
    session.close();
  }

  ArrayList<Software> findAll() {
    return (ArrayList<Software>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Software").list();
  }
}
