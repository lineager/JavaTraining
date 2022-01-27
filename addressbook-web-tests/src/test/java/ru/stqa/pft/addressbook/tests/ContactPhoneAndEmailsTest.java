package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Contact;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactPhoneAndEmailsTest extends TestBase {

    @Test
    public void testContactPhoneAndEmail(){
        app.goTo().returnToHomePage();
        //получаем информацию из полей контакта
        Contact contact = app.contact().all().iterator().next();
        Contact contactInfoFromEditForm = app.contact().infoInfoFromEditForm(contact);
        //проверяем поле Phone
        assertThat(contact.getAllPhones(),equalTo(mergePhones(contactInfoFromEditForm)));
        //проверяем поле Email
        assertThat(contact.getAllEmails(),equalTo(mergeEmails(contactInfoFromEditForm)));
        //проверяем поле Address
        assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));
    }

    private String mergePhones(Contact contact) {
       return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone() , contact.getHomePhone2())
               .stream().filter((s) -> ! s.equals(""))
               .map(ContactPhoneAndEmailsTest::cleaned)
               .collect(Collectors.joining("\n"));
    }

    private String mergeEmails(Contact contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactPhoneAndEmailsTest::cleanedWithSpace)
                .collect(Collectors.joining("\n"));
    }

    private String mergeAddress(Contact contact) {
        return Arrays.asList(contact.getAddress()).stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneAndEmailsTest::cleanedWithSpace)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone){
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }

    public static String cleanedWithSpace(String s){
        return s.trim().replaceAll(" +", " ");
    }
}
