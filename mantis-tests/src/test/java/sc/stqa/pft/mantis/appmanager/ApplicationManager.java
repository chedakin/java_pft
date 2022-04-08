package sc.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.Properties;

import static org.testng.Assert.fail;

public class ApplicationManager {

    private final Properties properties;
    private WebDriver driver;
    private String browser;
    public StringBuffer verificationErrors = new StringBuffer();
    private RegistrationHelper registrationHelper;
    private FtpHelper ftp;
    private MailHelper mailHelper;
    private JamesHelper jamesHelper;


    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        System.setProperty("webdriver.gecko.driver","C:\\JavaTmp\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver","C:\\JavaTmp\\chromedriver.exe");
        System.setProperty("webdriver.edge.driver","C:\\JavaTmp\\msedgedriver.exe");

    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }


    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null){
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public FtpHelper ftp() {
        if(ftp == null) {
            ftp = new FtpHelper(this);
        }
        return ftp;
    }

    public MailHelper mail() {
        if(mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public JamesHelper james() {
        if(jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            if (Objects.equals(browser, Browser.FIREFOX.browserName())) {
                driver = new FirefoxDriver();
            } else if (Objects.equals(browser, Browser.CHROME.browserName())) {
                driver = new ChromeDriver();
            } else if (Objects.equals(browser, Browser.EDGE.browserName())) {
                driver = new EdgeDriver();
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            driver.get(properties.getProperty("web.baseUrl"));
        }
        return driver;
    }
}
