package ru.stqa.pft.addressbook.tests;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Group;
import java.util.List;

public class DbConnectionTest {

    private SessionFactory sessionFactory;

    @BeforeClass
    protected void setUp()  {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @Test
    public void testGroupConnect(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Group> result = session.createQuery( "from Group").list();
        for ( Group group : result ) {
            System.out.println(group);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testContactConnect(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Contact> result = session.createQuery( "from Contact where deprecated = '0000-00-00'").list();
        for ( Contact contact : result ) {
            System.out.println(contact);
            System.out.println(contact.getGroups());
        }
        session.getTransaction().commit();
        session.close();
    }
}
