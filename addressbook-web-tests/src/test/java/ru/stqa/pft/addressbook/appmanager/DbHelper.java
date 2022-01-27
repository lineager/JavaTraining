package ru.stqa.pft.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import java.util.List;


public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Groups groups() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Group> result = session.createQuery( "from Group").list();
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public Contacts contacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Contact> result = session.createQuery( "from Contact where deprecated = '0000-00-00'").list();
        session.getTransaction().commit();
        session.close();
        return new Contacts(result);
    }

    public Groups findGroupByName(String nameGroup) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Group> result = session.createQuery( "from Group where group_name = " + nameGroup).list();
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public Contact findContactById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Contact contact = (Contact) session.createQuery(String.format("from Contact where id = %s", id)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return contact;
    }

}
