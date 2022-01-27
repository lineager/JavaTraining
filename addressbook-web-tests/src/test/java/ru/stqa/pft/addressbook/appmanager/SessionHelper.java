package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase{

    public SessionHelper(WebDriver wd) {
       super(wd);
    }
    //залогиниться
    public void login(String userName, String password) {
        type(By.name("user"),userName);
        type(By.name("pass"),password);
        click(By.xpath("//input[@value='Login']"));
    }
}
