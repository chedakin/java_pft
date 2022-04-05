package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.Contacts;
import sc.stqa.pft.addressbook.models.GroupData;
import sc.stqa.pft.addressbook.models.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTest extends TestBase {

    @BeforeTest
    public void createNewGroup() {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test2").withHeader("test2.2").withFooter("test2.3"));
    }

    @BeforeTest
    public void contactExists() {
        if (app.db().contacts().size() == 0) {
            app.goTo().addNew();
            app.contact().create(new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").
                    withAddress("L.Chaikinoi").withMobile("+79067762568"), true);

        }
        app.goTo().homePage();
    }

    @Test
    public void testAddContactsToGroup() {
        app.goTo().homePage();

        Contacts allContacts = app.db().contacts();
        ContactData contactToAdd = allContacts.iterator().next();
        Contacts addedContacts = new Contacts();
        addedContacts.add(contactToAdd);
        Groups allGroups = app.db().groups();
        GroupData group = allGroups.iterator().next();

        logger.info("contact to add " + contactToAdd);
        logger.info("group " + group);

        app.contact().addToGroup(contactToAdd, group);
        app.goTo().homePage();
        app.contact().viewAllContacts();

        assertThat(group.getContacts().size(), equalTo(1));
        assertThat(group.getContacts(), equalTo(addedContacts));
    }


}
