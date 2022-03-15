package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.getNavigationHelper().gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().gotoAddNew();
        app.getContactHelper().createContact(new ContactData("Serg", "Ched", "Mr", "L.Chaikinoi", "+79067762568", "test2"), true);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size(), after.size() - 1);
    }



}
