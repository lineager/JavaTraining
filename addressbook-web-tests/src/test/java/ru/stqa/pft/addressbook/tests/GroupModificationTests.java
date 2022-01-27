package ru.stqa.pft.addressbook.tests;


import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void preconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new Group().withName("testGroup1"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.db().groups();
        Group modifiedGroup = before.iterator().next();

        Group group = new Group().withId(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3");

        app.goTo().groupPage();
        app.group().modify(group);

        assertThat(app.group().count(), CoreMatchers.equalTo(before.size()));

        Groups after = app.db().groups();

        assertThat(after, equalTo(before.withOut(modifiedGroup).withAdded(group)));
        verifyGroupListInUI();
    }

}
