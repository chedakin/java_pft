package sc.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.Contacts;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver driver) {
        super(driver);
    }
    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("email"), contactData.getEmail());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
        attach(By.name("photo"), contactData.getPhoto());

        if (creation){
            if(contactData.getGroups().size()>0){
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            } else {
                Assert.assertFalse(isElementPresent(By.name("new_group")));
            }
        }
    }

    public void returnToContactPage() {
        click(By.linkText("home page"));
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void selectContactById(int id) {
        driver.findElement(By.cssSelector("input[value='" + id +"']")).click();
    }

    public void deleteContact() {
            acceptNextAlert = true;
            driver.findElement(By.xpath("//input[@value='Delete']")).click();
            assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    }

    public void initContactModificationById(int id) {
        driver.findElement(By.xpath(String.format("//a[@href='edit.php?id=%s']", id))).click();

        /*
        // ?????????????? ?????????????? ?????????? ????????????.
        //?????????????? ?????? ???????? ???? ID
        WebElement checkbox = driver.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        //?????????????????????? ???? 2 ???????????? ????????. ?????????? ????????????, ?? ?????????????? ?????? ????????
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        //?????????? ???????????? ?????????? ???? ???????? ????????????
        List<WebElement> cells = row.findElements(By.tagName("td"));
        // ???????? - ?????? ?????????????? ????????????. ?? ?????? ?????????????? ????????????
        cells.get(7).findElement(By.tagName("a")).click();
        */
    }

    public void submitContactModification() {
        driver.findElement(By.xpath("//div[@id='content']/form/input[22]")).click();
    }

    public void create(ContactData contact, boolean creation) {
        fillContactForm(contact, creation);
        submitContactCreation();
        contactsCache = null;
        returnToContactPage();
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactsCache = null;
        returnToContactPage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        contactsCache = null;
    }

    public void viewAllContacts() {
        driver.findElement(By.name("group")).click();
        new Select(driver.findElement(By.name("group"))).selectByVisibleText("[all]");
    }

    public void viewGroupContacts(GroupData group) {
        driver.findElement(By.name("group")).click();
        new Select(driver.findElement(By.name("group"))).selectByValue(""+ group.getId());
    }

    public void addToGroup(ContactData contact, GroupData group){

        selectContactById(contact.getId());
        driver.findElement(By.name("to_group")).click();
        new Select(driver.findElement(By.name("to_group"))).selectByValue(""+ group.getId());
        driver.findElement(By.name("add")).click();
    }

    public void deleteFromGroup(ContactData contact, GroupData group) {

        viewGroupContacts(group);
        driver.findElement(By.id(""+contact.getId())).click();
        driver.findElement(By.name("remove")).click();

    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return driver.findElements(By.name("selected[]")).size();
    }

    private Contacts contactsCache = null;

    public Contacts all() {
        if (contactsCache != null){
            return contactsCache;
        }
        contactsCache = new Contacts();
        List<WebElement> rows = driver.findElements(By.name("entry"));
        for(WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String address = cells.get(3).getText();
            String email = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).
                    withAddress(address).withEmail(email).withAllPhones(allPhones);
            contactsCache.add(contact);
        }

        return contactsCache;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = driver.findElement(By.name("firstname")).getAttribute("value");
        String lastname = driver.findElement(By.name("lastname")).getAttribute("value");
        String email = driver.findElement(By.name("email")).getAttribute("value");
        String address = driver.findElement(By.name("address")).getAttribute("value");
        String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
        String homePhone = driver.findElement(By.name("home")).getAttribute("value");
        String workPhone = driver.findElement(By.name("work")).getAttribute("value");
        driver.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).
                withAddress(address).withEmail(email).withMobile(mobile).withWorkPhone(workPhone).withHomePhone(homePhone);
    }


}
