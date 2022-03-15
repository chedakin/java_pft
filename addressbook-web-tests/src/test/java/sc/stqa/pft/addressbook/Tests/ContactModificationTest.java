package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() throws Exception {

        app.getNavigationHelper().gotoHomePage();

        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoAddNew();
            app.getContactHelper().createContact(new ContactData("Serg", "Ched", "Mr", "L.Chaikinoi", "+79067762568", "test2"), true);
            app.getNavigationHelper().gotoHomePage();
        }
        int before = app.getContactHelper().getContactCount();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Serg", "Ched", "Mr", "L.Chaikinoi", "+79067762568", null), false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();
        int after = app.getContactHelper().getContactCount();
        Assert.assertEquals(before, after);
    }
}
