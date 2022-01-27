package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase {

    public GroupHelper(WebDriver wd) {
        super(wd);
    }
    //сохранить группу
    public void submitGroupCreation() {
        click(By.name("submit"));
    }
    //заполнить поля создания группы
    public void fillGroupForm(Group groupDate) {
        type(By.name("group_name"), groupDate.getName());
        type(By.name("group_header"), groupDate.getHeader());
        type(By.name("group_footer"), groupDate.getFooter());
    }
    //создать новую группу
    public void initGroupCreation() {
        click(By.name("new"));
    }
    //кнопка удаления группы
    public void deleteGroups() {
        click(By.name("delete"));
    }

    //выбор в списке группы
    public void selectGroupById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    //нажать кнопку edit
    public void initGroupModification() {
        click(By.xpath("//*[@name=\"edit\"]"));
    }
    //нажать на кнопку update
    public void submitGroupModification() {
        click(By.xpath("//*[@name=\"update\"]"));
    }

    //вернутся на страницу групп
    public void returnToGroupPage() {
        click(By.linkText("group page"));
    }

    public boolean isThereAGroup() {
        return isElementPresent(By.name("selected[]"));
    }

    public void create(Group group) {
        initGroupCreation();
        fillGroupForm(group);
        submitGroupCreation();
        groupCache = null;
        returnToGroupPage();
    }

    public void modify(Group group) {
        selectGroupById(group.getId());
        initGroupModification();
        fillGroupForm(group);
        submitGroupModification();
        groupCache = null;
        returnToGroupPage();
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Groups groupCache = null;

    public Groups all() {
        if (groupCache != null){
        return new Groups(groupCache);
        }
        groupCache = new Groups();
        List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));
        for (WebElement element : elements){
            String name = element.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            groupCache.add(new Group().withId(id).withName(name));
        }
        return new Groups(groupCache);
    }

    public void delete(Group group) {
        selectGroupById(group.getId());
        deleteGroups();
        groupCache = null;
        returnToGroupPage();
    }
}
