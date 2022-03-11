package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import sc.stqa.pft.addressbook.appmanager.ApplicationManger;

import static org.testng.Assert.fail;

public class TestBase {

    protected final ApplicationManger app = new ApplicationManger();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

}
