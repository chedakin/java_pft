package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.ContactData;
import sc.stqa.pft.addressbook.models.Contacts;
import sc.stqa.pft.addressbook.models.GroupData;
import sc.stqa.pft.addressbook.models.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactFromGroupTest extends TestBase {

    @BeforeTest
    public void ensureGroupWithContactsExists() {
        Groups groups = app.db().groups();
        if(groups.size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test2").withHeader("test2.2").withFooter("test2.3"));
            groups = app.db().groups();
        }

 /*     Этот кусок рабочий, но в приложении есть баг: при удалении контакта, связь с группой в базе данных остается.
 В итоге есть группы с контактами, а контактов этих уже нет. В нормальной жизни надо баг создать, но в тестовом проекте пойдем от обратного:
 будем смотреть контакты и искать, в какие группы они добавлены.

        if(groups.stream().noneMatch((g) -> g.getContacts().size() > 0)) {
            GroupData lastGroup = groups.iterator().next();
            Contacts allContacts = app.db().contacts();
            ContactData contactToAdd = allContacts.iterator().next();
            app.goTo().homePage();
            app.contact().addToGroup(contactToAdd, lastGroup);
            app.goTo().homePage();
            app.contact().viewAllContacts();
        }
 */
        Contacts contacts = app.db().contacts();
        if (contacts.size() == 0) {
            app.goTo().addNew();
            app.contact().create(new ContactData().withFirstname("Serg").withLastname("Ched").withEmail("a@sct.ru").
                    withAddress("L.Chaikinoi").withMobile("+79067762568"), true);
            contacts = app.db().contacts();
        }

        if(contacts.stream().noneMatch((c) -> c.getGroups().size()>0)){
            app.goTo().homePage();
            app.contact().addToGroup(contacts.iterator().next(), groups.iterator().next());
        }
    }


    @Test
    public void testDeleteContactsFromGroup() {
        //По причине, описаной выше, пойдем со стороны контактов
        Contacts allContacts = app.db().contacts();
        ContactData testContact = allContacts.stream().filter((c) -> c.getGroups().size() != 0)
                .collect(Collectors.toSet()).iterator().next();
        logger.info("test contact " + testContact);
        Groups groupsBefore = testContact.getGroups();
        GroupData testGroup = groupsBefore.iterator().next();
        Contacts contactsBefore = testGroup.getContacts();
        logger.info("test group " + testGroup);

        app.goTo().homePage();
        app.contact().deleteFromGroup(testContact, testGroup);
        app.goTo().homePage();
        app.contact().viewAllContacts();

        Groups groupsAfter = (new ContactData().withId(testContact.getId())).getGroups();
        Contacts contactsAfter = (new GroupData().withId(testGroup.getId())).getContacts();

        logger.info("Groups Before " + groupsBefore);
        logger.info("Contacts Before" + contactsBefore);
        logger.info("Groups After" + groupsAfter);
        logger.info("Contacts After " + contactsAfter);

        assertThat(groupsAfter , equalTo(groupsBefore.without(testGroup)));
        assertThat(contactsAfter, equalTo(contactsBefore.without(testContact)));
    }


}
