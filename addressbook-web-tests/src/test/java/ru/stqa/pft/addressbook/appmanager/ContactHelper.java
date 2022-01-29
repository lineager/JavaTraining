package ru.stqa.pft.addressbook.appmanager;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.Contact;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import javax.xml.bind.Element;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    //заполнить поля контактов
    public void fillContactForm(Contact contactDate, boolean creation) {
        type(By.name("firstname"), contactDate.getName());
        type(By.name("middlename"), contactDate.getSurname());
        type(By.name("lastname"), contactDate.getLastName());
        type(By.name("email"), contactDate.getEmail());
        if(creation) {
            if (contactDate.getGroups().size() > 0){
                Assert.assertTrue(contactDate.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactDate.getGroups().iterator().next().getName());
            }
        }else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    //сохранить контакт
    public void submitNewContact() {
        click(By.name("submit"));
    }

    //выбрать первый контакт из списка
    public void selectContact(int index) {
        wd.findElements(By.xpath("//*[@id=\"maintable\"]//*[@id]")).get(index).click();
    }

    private void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    //изменить первый в списке контакт
    public void editContactButtonById(int id) {
         wd.findElement(By.cssSelector("input[value='" + id + "']")).findElement(By.xpath("//*[@title=\"Edit\"]")).click();
    }

    //кнопка сохранить редактирование
    public void editSaveContactButton() {
        click(By.xpath("//*[@value=\"Update\"]"));
    }

    //кнопка удалить контакт
    public void deleteContactButton() {
        click(By.xpath("//input[@value='Delete']"));
    }

    //нажать ок при удалении контакта
    public void selectOoForDeleteContact() {
        wd.switchTo().alert().accept();
    }

    public void createNewContact() {
        click(By.linkText("add new"));
    }
    //вернуться на страницу контактов
    public void returnHomePage() {
        click(By.linkText("home"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void createContact(Contact contact) {
        createNewContact();
        fillContactForm(contact, true);
        submitNewContact();
        contactCache = null;
        returnHomePage();
    }

    public void modifyContact(Contact contact) {
        editContactButtonById(contact.getId());
        fillContactForm(contact, false);
        editSaveContactButton();
        contactCache = null;
        returnHomePage();
    }

    public void contactToGroup(Contact contact, Group group) {
        selectContactById(contact.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(group.getId()));
        click(By.name("add"));
        contactCache = null;
    }

    public int count() {
      String value = wd.findElement(By.xpath("//*[@id=\"search_count\"]")).getText();
        return Integer.parseInt(value);
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null){
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements){
            List<WebElement> column = element.findElements(By.tagName("td"));
            String name = column.get(2).getText();
            String lastName = column.get(1).getText();
            String allPhones = column.get(5).getText();
            String allEmails = column.get(4).getText();
            String address = column.get(3).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new Contact().withId(id).withName(name).withLastName(lastName).withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address));
        }
        return  new Contacts(contactCache);
    }

    public void delete(Contact contact) {
        selectContactById(contact.getId());
        deleteContactButton();
        selectOoForDeleteContact();
        contactCache = null;
        returnHomePage();
    }

    public Contact infoInfoFromEditForm(Contact contact) {
        infoContactModificationById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String home2 = wd.findElement(By.name("phone2")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");

        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");

        wd.navigate().back();
        return new Contact().withId(contact.getId()).withName(firstName).withLastName(lastName).withHomePhone(home).withHomePhone2(home2).withWorkPhone(work).withMobilePhone(mobile).withEmail(email).withEmail2(email2).withEmail3(email3).withAddress(address);
    }

    private void infoContactModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();

     //   wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }
}