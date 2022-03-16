package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;
import java.util.Set;

public class ContactDeletionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().all().size() == 0){
            app.goTo().addNew();
            app.contact().create(new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").
                    withAddress("L.Chaikinoi").withMobile("+79067762568").withGroup("test2"), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {

        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();

        app.contact().delete(deletedContact);
        app.goTo().homePage();
        Set<ContactData> after = app.contact().all();

        Assert.assertEquals(before.size(), after.size() + 1);
        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }



}
