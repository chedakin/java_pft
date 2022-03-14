package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;

public class ContactDeletionTest extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {

        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteContact();
    }

}
