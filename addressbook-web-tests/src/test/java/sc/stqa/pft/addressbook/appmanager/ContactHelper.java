package sc.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import sc.stqa.pft.addressbook.models.ContactData;

import static org.testng.Assert.assertTrue;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }
    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("title"), contactData.getTitle());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobile());

        if (creation){
            new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void returnToContactPage() {
        click(By.linkText("home page"));
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void selectContact() {
        click(By.name("selected[]"));
    }

    public void deleteContact() {
        if (isAlertPresent()) {
            acceptNextAlert = true;
            driver.findElement(By.xpath("//input[@value='Delete']")).click();
            assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
        } else {
            return;
        }
    }

    public void initContactModification() {
       // driver.findElement(By.xpath("//table[@id='maintable']/tbody/tr[3]/td[8]/a/img")).click();
        driver.findElement(By.xpath("//img[@alt='Edit']")).click();
    }

    public void submitContactModification() {
        driver.findElement(By.xpath("//div[@id='content']/form/input[22]")).click();
    }

    public void createContact(ContactData contact, boolean creation) {
        fillContactForm(contact, creation);
        submitContactCreation();
        returnToContactPage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }
}
