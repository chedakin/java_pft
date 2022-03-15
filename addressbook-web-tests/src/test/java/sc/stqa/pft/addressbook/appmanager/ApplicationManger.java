package sc.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManger {

    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private SessionHelper sessionHelper;


    public WebDriver driver;
    public String baseUrl;

    public StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    public ApplicationManger(String browser) {
        this.browser = browser;
    }

    public void init() {
        System.setProperty("webdriver.gecko.driver","C:\\JavaTmp\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver","C:\\JavaTmp\\chromedriver.exe");
        System.setProperty("webdriver.edge.driver","C:\\JavaTmp\\msedgedriver.exe");
        if (Objects.equals(browser, Browser.FIREFOX.browserName())) {
            driver = new FirefoxDriver();
        } else if (Objects.equals(browser, Browser.CHROME.browserName())) {
            driver = new ChromeDriver();
        } else if (Objects.equals(browser, Browser.EDGE.browserName())) {
            driver = new EdgeDriver();
        }

        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.get("http://localhost/addressbook/");
        groupHelper = new GroupHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        sessionHelper = new SessionHelper(driver);
        contactHelper = new ContactHelper(driver);

        sessionHelper.login("admin", "secret");
    }


    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
          fail(verificationErrorString);
        }
    }


    public GroupHelper getGroupHelper() {
        return groupHelper;
    }

    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }

    public ContactHelper getContactHelper() {
        return contactHelper;
    }
}
