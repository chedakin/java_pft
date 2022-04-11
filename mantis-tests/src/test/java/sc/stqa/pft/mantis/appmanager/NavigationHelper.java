package sc.stqa.pft.mantis.appmanager;


import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String username, String password) {
        type(By.name("username"), username);
        click(By.xpath("//input[@value='Login']"));
        type(By.name("password"), password);
        click(By.xpath("//input[@value='Login']"));
    }

    public void manageUser() {
        click(By.linkText("Manage Users"));
    }

    public void editUser(String username) {
        manageUser();
        type(By.id("search"), username);
        click(By.xpath("//input[@value='Apply Filter']"));
        click(By.linkText(username));
    }

    public void resetUserPassword(){
        click(By.xpath("//input[@value='Reset Password']"));
    }

    public void confirmChangesViaEmail(String confirmationLink, String password) {
        driver.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.tagName("button"));
    }
}
