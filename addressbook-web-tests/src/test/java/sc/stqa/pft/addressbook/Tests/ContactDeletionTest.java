package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;

public class ContactDeletionTest extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        app.getNavigationHelper().gotoHomePage();

        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoAddNew();
            app.getContactHelper().createContact(new ContactData("Serg", "Ched", "Mr", "L.Chaikinoi", "+79067762568", "test2"), true);
            app.getNavigationHelper().gotoHomePage();
        }

        app.getContactHelper().selectContact();
        app.getContactHelper().deleteContact();
    }

}
