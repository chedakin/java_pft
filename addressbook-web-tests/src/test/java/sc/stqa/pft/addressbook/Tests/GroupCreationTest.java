package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.List;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    app.getGroupHelper().createGroup(new GroupData("test2", null, null));
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);
  }

}
