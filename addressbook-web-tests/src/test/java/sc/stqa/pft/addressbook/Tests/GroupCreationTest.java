package sc.stqa.pft.addressbook.Tests;

import org.testng.Assert;
import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.GroupData;

import java.util.Set;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("group4");
    app.group().create(group);
    Set<GroupData> after = app.group().all();

    Assert.assertEquals(after.size(), before.size() + 1);

    group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt());
    before.add(group);
    Assert.assertEquals(before, after);
  }

}
