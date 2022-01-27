package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.TestBase;
import ru.stqa.pft.addressbook.model.Group;
import ru.stqa.pft.addressbook.model.Groups;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupFromXml() throws IOException {
       try( BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))) {
           String xml = "";
           String line = reader.readLine();
           while (line != null) {
               xml += line;
               line = reader.readLine();
           }
           XStream xStream = new XStream();
           xStream.processAnnotations(Group.class);
           List<Group> groups = (List<Group>) xStream.fromXML(xml);
           return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
       }
    }

    @DataProvider
    public Iterator<Object[]> validGroupFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))){
            String json = "";
            String line = reader.readLine();
            while (line != null){
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<Group> groups = gson.fromJson(json, new TypeToken<List<Group>>() {}.getType());
            return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }

    @Test(dataProvider = "validGroupFromJson")
    public void testGroupCreation(Group group){
        Groups before = app.db().groups();

        app.goTo().groupPage();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() + 1));

        Groups after = app.db().groups();

        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

    @Test
    public void testBadGroupCreation(){
        Groups before = app.db().groups();

        Group group = new Group().withName("testGroup1'");
        app.goTo().groupPage();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size()));

        Groups after = app.db().groups();
        assertThat(after, equalTo(before));
    }
}