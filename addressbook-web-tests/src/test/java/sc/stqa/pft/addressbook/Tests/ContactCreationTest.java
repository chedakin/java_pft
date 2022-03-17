package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.goTo().addNew();
        ContactData contact = new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").withAddress("L.Chaikinoi").withMobile("+79067762568").withGroup("test2");
        app.contact().create(contact, true);
        Contacts after = app.contact().all();

        ContactData contactToBefore = new ContactData().withFirstname
                (contact.getLastname() + " " + contact.getFirstname() + " " + contact.getAddress() + " " + contact.getEmail() + " " + contact.getMobile());
        contactToBefore.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt());

        assertThat(after.size()-1, equalTo(before.size()));
        assertThat(after, equalTo(before.withAdded(contactToBefore)));
    }



}
