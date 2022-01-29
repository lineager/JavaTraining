package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeletedFromGroupTest extends TestBase {

    @BeforeMethod
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
    public void deleteFromGroupTest() {
        app.goTo().returnToHomePage();
        Contact contact = selectedContact();
        Group deletedGroup = selectedGroup(contact);
        Groups before = contact.getGroups();
        app.goTo().returnToHomePage();
        app.contact().selectGroup(deletedGroup.getId());
        app.contact().deleteContactFromGroup(contact, deletedGroup.getId());
        Contact contacts = selectContact(contact);
        Groups after = contacts.getGroups();
        assertThat(after, equalTo(before.withOut(deletedGroup)));

    }

    private Contact selectContact(Contact contact) {
        Contacts contacts = app.db().contacts();
        return contacts.iterator().next().withId(contact.getId());
    }

    private Group selectedGroup(Contact c) {
        Contact contact = selectContact(c);
        Groups returnGroup = contact.getGroups();
        return returnGroup.iterator().next();
    }

    private Contact selectedContact() {
        Contacts contacts = app.db().contacts();
        for (Contact contact : contacts) {
            if (contact.getGroups().size() > 0) {
                return contact;
            }
        }
        Contact returnContact = app.db().contacts().iterator().next();
        app.contact().contactToGroup(returnContact, app.db().groups().iterator().next());
        return returnContact;

    }

}
