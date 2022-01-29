package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Contact;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class For9taskTest extends TestBase {

    @Test
    public void createContactTest() {
        app.goTo().returnToHomePage();
        List<Contact> before = app.contact().getContactList();
        Contact contact = new Contact().withName("Петр").withSurname("Петров").withLastName( "Петрович").withEmail("petya@gmail.com");
        app.contact().createContact(contact);
        List<Contact> after = app.contact().getContactList();;
        Assert.assertEquals(after.size(), before.size() + 1);
    }

    @Test
    public void modifyContactTest() {
        app.goTo().returnToHomePage();
        if (!app.contact().isThereAContact()){
            Contact contact = new Contact().withName("Петр").withSurname("Петров").withLastName( "Петрович").withEmail("petya@gmail.com");
            app.contact().createContact(contact);
        }

        List<Contact> before = app.contact().getContactList();
        app.contact().editContactButtonById(before.size() - 1);
        Contact user = new Contact().withId(before.get(before.size() - 1).getId()).withName("Сергей").withSurname("Сергеев").withLastName( "Сергеевич").withEmail("sereja@gmail.com");
        app.contact().fillContactForm(user, false);
        app.contact().editSaveContactButton();
        app.goTo().returnToHomePage();
        List<Contact> after = app.contact().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(user);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }

    @Test
    public void deleteContactTest() {
        app.goTo().returnToHomePage();
        if (!app.contact().isThereAContact()){
            Contact contact = new Contact().withName("Петр").withSurname("Петров").withLastName( "Петрович").withEmail("petya@gmail.com");
            app.contact().createContact(contact);
        }

        List<Contact> before = app.contact().getContactList();
        app.contact().editContactButtonById(before.size() - 1);
        app.contact().deleteContactButton();
        app.contact().selectOKForDeleteContact();
        List<Contact> after = app.contact().getContactList();;
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
