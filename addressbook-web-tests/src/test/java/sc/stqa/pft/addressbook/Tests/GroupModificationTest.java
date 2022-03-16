package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTest extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().list().size() == 0){
            app.group().create(new GroupData().withName("test2"));
            app.goTo().groupPage();
        }
    }

    @Test
    public void testGroupModification() {

        List<GroupData> before = app.group().list();
        GroupData group = new GroupData().withId(before.get(before.size()-1).getId()).withName("test2").withHeader("test2.2").withFooter("test2.3");
        int index = before.size() -1;
        app.group().modify(group, index);
        List<GroupData> after = app.group().list();

        Assert.assertEquals(after.size(), before.size() );
        before.remove(index );
        before.add(group);

        Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

    }

}
