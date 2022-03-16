package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.*;

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

        Set<GroupData> before = app.group().all();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("test2").withHeader("test2.2").withFooter("test2.3");
        app.group().modify(group);
        Set<GroupData> after = app.group().all();

        Assert.assertEquals(after.size(), before.size() );
        before.remove(modifiedGroup );
        before.add(group);

        Assert.assertEquals(before, after);

    }

}
