package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import org.openqa.selenium.json.TypeToken;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeTest
    public void preconditions(){
        if (app.db().findGroupByName("'testGroup1'").isEmpty()) {
            app.goTo().groupPage();
            app.group().create(new Group().withName("testGroup1"));
        }
    }

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
       try( BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))){
           String json = "";
           String line = reader.readLine();
           while (line != null){
               json += line;
               line = reader.readLine();
           }
           Gson gson = new Gson();
           List<Contact> contacts = gson.fromJson(json, new TypeToken<List<Contact>>() {}.getType());
           return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
       }
    }

    @Test(dataProvider = "validContacts")
    public void contactCreateTest(Contact contact) {
        Groups groups = app.db().groups();


        Contacts before = app.db().contacts();
        app.contact().createNewContact();
        app.contact().createContact(contact.inGroup(groups.iterator().next()));
        Assert.assertEquals(app.contact().count(), before.size() + 1);

        Contacts after = app.db().contacts();
        assertThat(after, equalTo(
                before.withAdded( contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test
    public void contactBadCreateTest() {
        Contacts before = app.db().contacts();
        app.contact().createNewContact();
        Contact contact = new Contact().withName("Василий'").withSurname("Огурцов'").withLastName("Петрович").withEmail("vasya@gmail.com");
        app.contact().createContact(contact);
        Assert.assertEquals(app.contact().count(), before.size());
        Contacts after =app.db().contacts();

        assertThat(after, equalTo(before));
    }
}