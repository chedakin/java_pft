package sc.stqa.pft.mantis.Tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import sc.stqa.pft.mantis.Models.MailMessage;
import sc.stqa.pft.mantis.Models.UserData;
import sc.stqa.pft.mantis.Models.Users;
import sc.stqa.pft.mantis.appmanager.HttpSession;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends  TestBase{

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangeUserPassword() throws MessagingException, IOException {
        //Select user to update password
        Users users = app.db().users();

        if(users.size() <= 1 ){
            System.out.println("Create some user first");
            return;
        }

        UserData user =  users.stream().filter((u) -> !u.getUsername().equals("administrator"))
                .collect(Collectors.toList()).iterator().next();

        if(user.getUsername().equals("administrator")){
            System.out.println("Administrator selected!!! Won't change password for admin");
            return;
        }

        logger.info("User to reset password: ", user);

        //Login as administrator
        app.goTo().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));

        //Navigate to Manage Users page and edit necessary user
        app.goTo().editUser(user.getUsername());

        //Reset password for user
        app.goTo().resetUserPassword();

        //Check email and click confirmation link.
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);

        logger.info("Emails received: ");
        for(MailMessage mail : mailMessages) {
            logger.info("to: " + mail.to + "\n") ;
            logger.info("Body: "+ mail.text +"\n");
        }

        String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
        app.goTo().confirmChangesViaEmail(confirmationLink, "newpassword");

        //Check that user is able to login with new password

        HttpSession session = app.newSession();
        assertTrue(session.login(user.getUsername(), "newpassword"));
        assertTrue(session.isLoggedInAs(user.getUsername()));

    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
