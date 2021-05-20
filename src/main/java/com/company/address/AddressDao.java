package com.company.address;

import com.company.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class AddressDao {
    Address findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Address.class, id);
    }

    void save(Address address) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(address);
        tx1.commit();
        session.close();
    }

    void update(Address address) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(address);
        tx1.commit();
        session.close();
    }

    void delete(Address address) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(address);
        tx1.commit();
        session.close();
    }

    ArrayList<Address> findAll() {
        return (ArrayList<Address>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Address").list();
    }
}
