package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.getNavigationHelper().gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getNavigationHelper().gotoAddNew();
        ContactData contact = new ContactData("Serg", "Ched", "a@sct.ru", "L.Chaikinoi", "+79067762568", "test2");
        app.getContactHelper().createContact(contact, true);
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(before.size(), after.size() - 1);
        ContactData contactToBefore = new ContactData(null, null, null, null, null, null);
        contactToBefore.setFirstname(contact.getLastname() + " " + contact.getFirstname() + " " + contact.getAddress() + " " + contact.getEmail() + " " + contact.getMobile());

        int max = after.stream().max(Comparator.comparingInt(ContactData:: getId)).get().getId();

        contactToBefore.setId(max);
        before.add(contactToBefore);
        Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));
    }



}
