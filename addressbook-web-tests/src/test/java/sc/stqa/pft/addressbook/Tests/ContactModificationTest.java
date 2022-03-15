package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;

import java.util.List;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() throws Exception {

        app.getNavigationHelper().gotoHomePage();

        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoAddNew();
            app.getContactHelper().createContact(new ContactData("Serg", "Ched", "Mr", "L.Chaikinoi", "+79067762568", "test2"), true);
            app.getNavigationHelper().gotoHomePage();
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Serg", "Ched", "Mr", "L.Chaikinoi", "+79067762568", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(before.size(), after.size());
    }
}
