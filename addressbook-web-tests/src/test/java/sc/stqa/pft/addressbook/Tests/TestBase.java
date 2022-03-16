package sc.stqa.pft.addressbook.Tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import sc.stqa.pft.addressbook.appmanager.ApplicationManger;

import static org.testng.Assert.fail;

public class TestBase {

    protected static final ApplicationManger app = new ApplicationManger(Browser.CHROME.browserName());

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

}
