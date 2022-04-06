package sc.stqa.pft.mantis.Tests.appmanager.Tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import sc.stqa.pft.mantis.appmanager.ApplicationManger;


public class TestBase {

    protected static final ApplicationManger app = new ApplicationManger(System.getProperty("browser", Browser.CHROME.browserName()));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }
}


