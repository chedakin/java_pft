package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0){
            app.goTo().addNew();
            app.contact().create(new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").
                    withAddress("L.Chaikinoi").withMobile("+79067762568"), true);
        }
        app.goTo().homePage();
    }

    @Test
    public void testContactDeletion() throws Exception {

        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();

        app.contact().delete(deletedContact);
        app.goTo().homePage();

        //assertThat(app.contact().count()+1, equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after.size()+1, equalTo(before.size()));
        assertThat(after, equalTo(before.without(deletedContact)));

        verifyContactListInUI();
    }



}
