package sc.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(By.name("user"), username);
        click(By.xpath("//form[@id='LoginForm']/label[2]"));
        type(By.name("pass"), password);
        click(By.xpath("//input[@value='Login']"));
    }
}
