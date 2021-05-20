package com.company.util;

import com.company.address.Address;
import com.company.category.Category;
import com.company.game.Game;
import com.company.software.Software;
import com.company.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateSessionFactoryUtil {
  private static SessionFactory sessionFactory;

  private HibernateSessionFactoryUtil() {}

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration().configure();
//        Properties properties = new Properties();

//        properties.put(Environment.SHOW_SQL, "true");
        configuration.addAnnotatedClass(Software.class);
        configuration.addAnnotatedClass(Game.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Address.class);

//        configuration.setProperties(properties);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

      } catch (Exception e) {
        System.out.println("Исключение! " + e);
      }
    }
    return sessionFactory;
  }
}