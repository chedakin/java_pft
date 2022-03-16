package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.goTo().homePage();
        Set<ContactData> before = app.contact().all();
        app.goTo().addNew();
        ContactData contact = new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").withAddress("L.Chaikinoi").withMobile("+79067762568").withGroup("test2");
        app.contact().create(contact, true);
        Set<ContactData> after = app.contact().all();

        Assert.assertEquals(before.size(), after.size() - 1);

        ContactData contactToBefore = new ContactData().withFirstname(contact.getLastname() + " " + contact.getFirstname() + " " + contact.getAddress() + " " + contact.getEmail() + " " + contact.getMobile());
        contactToBefore.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt());
        before.add(contactToBefore);
        Assert.assertEquals(after, before);
    }



}
