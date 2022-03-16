package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;
import java.util.Set;

public class ContactModificationTest extends TestBase {

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
    public void testContactModification() throws Exception {

        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Serg").withLastname("Ched").
                withEmail("a2@sct.ru").withAddress("L.Chaikinoi").withMobile("+79067762568");

        app.contact().modify(contact);
        Set<ContactData> after = app.contact().all();

        ContactData contactBefore = new ContactData().withFirstname(contact.getLastname() + " " + contact.getFirstname() + " " + contact.getAddress() + " " + contact.getEmail() + " " + contact.getMobile()).
                withId(contact.getId());

        Assert.assertEquals(before.size(), after.size());
        before.remove(modifiedContact);
        before.add(contactBefore);

        Assert.assertEquals(after, before);
    }


}
