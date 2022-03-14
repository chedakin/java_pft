package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.GroupData;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.getNavigationHelper().gotoAddNew();
        app.getContactHelper().fillContactForm(new ContactData("Serg", "Ched", "Mr", "L.Chaikinoi", "+79067762568"));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().returnToContactPage();

    }



}
