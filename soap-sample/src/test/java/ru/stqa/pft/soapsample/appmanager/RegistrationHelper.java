package ru.stqa.pft.soapsample.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase{


    public RegistrationHelper(ApplicationManager app) {
        super(app);
    }

    public void registerNewUser(String username, String email)  {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        //click(By.cssSelector("input[value='Signup']"));
        click(By.xpath("//*[@type=\"submit\"]"));
    }


    public void confirmNewUser(String link, String password) {
        wd.get(link);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//*[@type=\"submit\"]"));
    }

    public void loginUser(String username, String password) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.xpath("//*[@type=\"submit\"]"));
    }

    public void selectUserOnTableAndResetPassword(String username) throws InterruptedException {
        wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
      //  wd.findElement(By.xpath("//table/tbody")).findElement(By.linkText(username)).click();
        click(By.linkText(username));
        click(By.xpath("//*[@id=\"manage-user-reset-form\"]/.//*[@type=\"submit\"]"));
    }
}
