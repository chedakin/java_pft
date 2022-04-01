package sc.stqa.pft.addressbook.Tests;

import org.testng.annotations.*;
import sc.stqa.pft.addressbook.models.GroupData;
import sc.stqa.pft.addressbook.models.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if(app.db().groups().size() == 0){
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("group2"));
    }
    app.goTo().groupPage();
  }

  @Test
  public void testGroupDeletion() throws Exception {

    Groups before = app.db().groups();
    GroupData deletedGroup = before.iterator().next();
    app.group().delete(deletedGroup);

    assertThat(app.group().count(), equalTo(before.size()-1));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(deletedGroup)));

    verifyGroupListInUI();

  }
}



