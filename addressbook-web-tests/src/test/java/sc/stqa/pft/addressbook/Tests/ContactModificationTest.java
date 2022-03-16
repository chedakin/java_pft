package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();

        if (app.contact().list().size() == 0){
            app.goTo().addNew();
            app.contact().create(new ContactData("Serg", "Ched", "a@sct.ru", "L.Chaikinoi", "+79067762568", "test2"), true);
            app.goTo().homePage();
        }
    }


    @Test
    public void testContactModification() throws Exception {

        List<ContactData> before = app.contact().list();
        int index = before.size()-1;
        ContactData contact = new ContactData(before.get(index).getId(), "Serg", "Ched", "a2@sct.ru", "L.Chaikinoi", "+79067762568", null);

        app.contact().modify(index, contact);
        List<ContactData> after = app.contact().list();

        ContactData contactBefore = new ContactData(null, null, null, null, null, null);
        contactBefore.setFirstname(contact.getLastname() + " " + contact.getFirstname() + " " + contact.getAddress() + " " + contact.getEmail() + " " + contact.getMobile());
        contactBefore.setId(contact.getId());

        Assert.assertEquals(before.size(), after.size());
        before.remove(index);
        before.add(contactBefore);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(after, before);
    }


}
