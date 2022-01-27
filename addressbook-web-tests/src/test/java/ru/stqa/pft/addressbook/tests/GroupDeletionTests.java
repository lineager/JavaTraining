package ru.stqa.pft.addressbook.tests;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void preconditions(){
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new Group().withName("testGroup1"));
        }
    }

    @Test
    public void testGroupDeletion() {
        Groups before = app.db().groups();
        Group deletedGroup = before.iterator().next();

        app.goTo().groupPage();
        app.group().delete(deletedGroup);
        assertThat(app.group().count(), CoreMatchers.equalTo(before.size() - 1));

        Groups after = app.db().groups();

        assertThat(after, equalTo(before.withOut(deletedGroup)));
    }
}
