package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void preconditions(){
        if (app.db().contacts().size() == 0) {
            app.goTo().returnToHomePage();
            app.contact().createContact(new Contact().withName("Василий").withSurname("Огурцов").withLastName("Петрович").withEmail("vasya@gmail.com"));
        }
    }

    @Test
    public void testContactModification(){
        Contacts before = app.db().contacts();
        Contact modifiedContact = before.iterator().next();

        Contact contact = new Contact().withId(modifiedContact.getId()).withName("Петр").withSurname("Петров").withLastName("Петрович").withEmail("vasya@com.com");
        app.goTo().returnToHomePage();
        app.contact().modifyContact(contact);
        Assert.assertEquals(app.contact().count(), before.size());

        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withOut(modifiedContact).withAdded(contact)));
    }
}