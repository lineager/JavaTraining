package ru.stqa.pft.soapsample.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.soapsample.model.MailMessage;

import java.io.IOException;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTest extends TestBase{

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testResetPassword() throws IOException, InterruptedException {
        String user = app.getProperty("mantis.user");
        String email = String.format(app.getProperty("mantis.email"));
        String password = app.getProperty("mantis.password");
        app.registration().loginUser(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        app.registration().selectUserOnTableAndResetPassword(user);
        List<MailMessage> messages = app.mail().waitForMail(1, 10000);
        String link = findConfirmationLink(messages, email);
        app.registration().confirmNewUser(link, password);
        assertTrue(app.newSession().login(user, password));
    }
    public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
