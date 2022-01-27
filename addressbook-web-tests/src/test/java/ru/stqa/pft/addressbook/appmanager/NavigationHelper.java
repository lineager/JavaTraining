package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase{

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }
    //перейти на страницу создания новой группы
    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))) {
           return;
        }
        click(By.linkText("groups"));
    }

    //перейти на страницу создания нового контакта

    //перейти на главную страницу(контакты)
    public void returnToHomePage() {
        if (isElementPresent(By.tagName("maintable"))){
            return;
        }
      click(By.linkText("home"));
    }
}
