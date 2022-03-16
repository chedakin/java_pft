package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.goTo().homePage();
        List<ContactData> before = app.contact().list();
        app.goTo().addNew();
        ContactData contact = new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").withAddress("L.Chaikinoi").withMobile("+79067762568").withGroup("test2");
        app.contact().create(contact, true);
        List<ContactData> after = app.contact().list();

        Assert.assertEquals(before.size(), after.size() - 1);
        ContactData contactToBefore = new ContactData().withFirstname(contact.getLastname() + " " + contact.getFirstname() + " " + contact.getAddress() + " " + contact.getEmail() + " " + contact.getMobile());

        before.add(contactToBefore);

        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }



}
