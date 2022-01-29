package ru.stqa.pft.soapsample.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.soapsample.model.MailMessage;

import java.io.IOException;
import java.util.List;

public class RegistrationTest extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void registrationTest() throws IOException {
        long now = System.currentTimeMillis();
        String user1 = String.format("user%s", now);
        String password = "password" ;
        String email = String.format("user%s@mail.ru",now);
       // app.james().createUser(user1, password);
        app.registration().registerNewUser(user1, email);
        List<MailMessage> messages = app.mail().waitForMail(2, 10000);
     //   List<MailMessage> messages = app.james().waitForMail(user1, password, 60000);
        String link = findConfirmationLink(messages, email);
        app.registration().confirmNewUser(link, password);
        Assert.assertTrue(app.newSession().login(user1,password));
    }

    private String findConfirmationLink(List<MailMessage> messages, String email) {
        MailMessage message = messages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(message.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
