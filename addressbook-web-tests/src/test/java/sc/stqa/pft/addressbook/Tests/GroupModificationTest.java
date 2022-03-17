package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.GroupData;
import sc.stqa.pft.addressbook.models.Groups;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupModificationTest extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0){
            app.group().create(new GroupData().withName("test2"));
            app.goTo().groupPage();
        }
    }

    @Test
    public void testGroupModification() {

        Groups before = app.group().all();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("test2").withHeader("test2.2").withFooter("test2.3");
        app.group().modify(group);

        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.group().all();
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));

    }

}
