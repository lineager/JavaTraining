package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void precondition(){
        if (app.db().contacts().size() == 0) {
            app.goTo().returnToHomePage();
            app.contact().createContact(new Contact().withName("Василий").withSurname("Огурцов").withLastName("Петрович").withEmail("vasya@gmail.com"));
        }
    }

    @Test
    public void contactDeleteTest()  {
        Contacts before = app.db().contacts();
        Contact deletedContact = before.iterator().next();

        app.goTo().returnToHomePage();
        app.contact().delete(deletedContact);
        Assert.assertEquals(app.contact().count(), before.size() - 1);

        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withOut(deletedContact)));
    }
}