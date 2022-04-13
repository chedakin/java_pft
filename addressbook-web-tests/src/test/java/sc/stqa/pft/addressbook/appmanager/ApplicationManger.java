package sc.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

import static org.testng.Assert.fail;

public class ApplicationManger {

    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private SessionHelper sessionHelper;

    private final Properties properties;
    public WebDriver driver;
    public String baseUrl;

    public StringBuffer verificationErrors = new StringBuffer();
    private String browser;
    private DBHelper dbHelper;

    public ApplicationManger(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        dbHelper = new DBHelper();

        if("".equals(properties.getProperty("selenium.server"))) {

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
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win10")));
            driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")),capabilities);
        }



        baseUrl = "https://www.google.com/";
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.get(properties.getProperty("web.baseUrl"));
        groupHelper = new GroupHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        sessionHelper = new SessionHelper(driver);
        contactHelper = new ContactHelper(driver);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));

    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
          fail(verificationErrorString);
        }
    }


    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public DBHelper db() {
        return dbHelper;
    }
}
