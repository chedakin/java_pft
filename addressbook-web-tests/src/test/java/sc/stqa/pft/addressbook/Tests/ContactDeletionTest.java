package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;

import java.util.List;

public class ContactDeletionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().list().size() == 0){
            app.goTo().addNew();
            app.contact().create(new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").
                    withAddress("L.Chaikinoi").withMobile("+79067762568").withGroup("test2"), true);
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {

        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;

        app.contact().delete(index);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();

        Assert.assertEquals(before.size(), after.size() + 1);
        before.remove(index );
        Assert.assertEquals(before, after);
    }



}
