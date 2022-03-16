package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactModificationTest extends TestBase {

    @Test
    public void testContactModification() throws Exception {

        app.getNavigationHelper().gotoHomePage();

        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoAddNew();
            app.getContactHelper().createContact(new ContactData("Serg", "Ched", "a@sct.ru", "L.Chaikinoi", "+79067762568", "test2"), true);
            app.getNavigationHelper().gotoHomePage();
        }

        List<ContactData> before = app.getContactHelper().getContactList();
        ContactData contact = new ContactData(before.get(before.size()-1).getId(), "Serg", "Ched", "a2@sct.ru", "L.Chaikinoi", "+79067762568", null);

        app.getContactHelper().initContactModification(before.size() - 1);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getContactHelper().returnToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();


        ContactData contactBefore = new ContactData(null, null, null, null, null, null);
        contactBefore.setFirstname(contact.getLastname() + " " + contact.getFirstname() + " " + contact.getAddress() + " " + contact.getEmail() + " " + contact.getMobile());
        contactBefore.setId(contact.getId());
        Assert.assertEquals(before.size(), after.size());
        before.remove(before.size()-1);
        before.add(contactBefore);
        Assert.assertEquals(new HashSet<Object>(after), new HashSet<Object>(before));
    }
}
