package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.List;

public class GroupModificationTest extends TestBase{

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().isThereAGroup()){
            app.getGroupHelper().createGroup(new GroupData("test2", null, null));
            app.getNavigationHelper().gotoGroupPage();
        }

        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size() -1);
        app.getGroupHelper().initGroupModification();
        app.getGroupHelper().fillGroupForm(new GroupData("test2", "test2.2", "test2.3"));
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() );

    }
}
