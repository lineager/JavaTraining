package ru.stqa.pft.addressbook.tests;

import com.sun.org.glassfish.gmbal.Description;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Collection;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactsAddToGroup extends TestBase {

    @BeforeTest
    public void preconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().returnToHomePage();
            app.contact().createContact(new Contact().withName("Василий").withSurname("Огурцов").withLastName("Петрович").withEmail("vasya@gmail.com"));
        } else if (app.db().findGroupByName("'testGroup1'").isEmpty()) {
            app.goTo().groupPage();
            app.group().create(new Group().withName("testGroup1"));
        }
    }

    @Test
    @Description("Добавить контакт в группу")
    public void addToGroupTest () {
        app.goTo().returnToHomePage();
        Contact contact = newContact();
        Groups before = contact.getGroups();
        Group group = groupForContact(contact);
        app.contact().contactToGroup(contact, group);
        Groups after = app.db().findContactById(contact.getId()).getGroups();
        assertThat(after, equalTo(before.withAdded(group)));
    }

    public Contact newContact() {
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        for (Contact contact : contacts) {
            if (contact.getGroups().size() < groups.size()) {
                return contact;
            }
        }
        app.goTo().groupPage();
        app.group().create(new Group().withName("test1"));
        app.goTo().returnToHomePage();
        return contacts.iterator().next();
    }

    public Group groupForContact(Contact contact) {
        Groups all = app.db().groups();
        Collection<Group> groups = new HashSet<>(all);
        groups.removeAll(contact.getGroups());
        return groups.iterator().next();
    }
}