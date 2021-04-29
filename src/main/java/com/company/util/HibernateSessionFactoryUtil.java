package com.company.util;

import com.company.category.Category;
import com.company.game.Game;
import com.company.software.Software;
import com.company.user.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
  private static SessionFactory sessionFactory;

  private HibernateSessionFactoryUtil() {}

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration().configure();

        configuration.addAnnotatedClass(Software.class);
        configuration.addAnnotatedClass(Game.class);
        configuration.addAnnotatedClass(Category.class);
        configuration.addAnnotatedClass(User.class);

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