package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.Contacts;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactCreationTest extends TestBase {

    @Test
    public void testContactCreation() throws Exception {

        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.goTo().addNew();
        ContactData contact = new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru")
                .withAddress("L.Chaikinoi").withMobile("+79067762568").withGroup("test2");
        app.contact().create(contact, true);

        assertThat(app.contact().count()-1, equalTo(before.size()));
        Contacts after = app.contact().all();
        contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt());
        assertThat(after, equalTo(before.withAdded(contact.withAllPhones(mergePhones(contact)))));
    }

    public String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobile(), contact.getWorkPhone())
                .stream().filter((s) -> s != null)
                .filter((s) -> ! s.equals(""))
                .map(ContactPhoneTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone){
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }





}
