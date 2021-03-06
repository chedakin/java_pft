package sc.stqa.pft.mantis.Tests;

import org.testng.annotations.Test;
import sc.stqa.pft.mantis.appmanager.HttpSession;
import java.io.IOException;
import static org.testng.Assert.assertTrue;


public class LoginTests extends TestBase {

    @Test
    public void testLogin() throws IOException {

        HttpSession session = app.newSession();
        assertTrue(session.login("administrator", "root1"));
        assertTrue(session.isLoggedInAs("administrator"));

    }
}
