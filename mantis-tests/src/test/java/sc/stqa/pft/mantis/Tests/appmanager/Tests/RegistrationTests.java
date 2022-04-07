package sc.stqa.pft.mantis.Tests.appmanager.Tests;

import org.testng.annotations.Test;

public class RegistrationTests extends TestBase {

    @Test
    public void testRegistration() {
        app.registration().start("user1", "user@localdomain.net");
    }

}
