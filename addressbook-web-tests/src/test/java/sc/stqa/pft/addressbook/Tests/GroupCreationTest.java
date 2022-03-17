package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.GroupData;
import sc.stqa.pft.addressbook.models.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTest extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {

    app.goTo().groupPage();

    Groups before = app.group().all();
    GroupData group = new GroupData().withName("group4");
    app.group().create(group);

    assertThat(app.group().count(), equalTo(before.size()+1));
    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));

  }

  @Test
  public void testBadGroupCreation() throws Exception {

    app.goTo().groupPage();

    Groups before = app.group().all();
    GroupData group = new GroupData().withName("groupBS'");
    app.group().create(group);

    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));

  }
}
