package ru.stqa.pft.soapsample.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.soapsample.model.Issue;
import ru.stqa.pft.soapsample.model.Project;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class SoapTest extends TestBase{
    //Для задания 17
    @Test
    public void getProjectsTest() throws IOException, ServiceException {
        skipIfNotFixed(9);
        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }


    @Test
    public void createIssueTest() throws MalformedURLException, RemoteException, ServiceException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue().withSummary("Test issue")
                .withDescription("Test issue description").withProject(projects.iterator().next());
        Issue created = app.soap().addIssue(issue);
        app.soap().getIssueStatus(issue.getId());
        assertEquals(issue.getSummary(), created.getSummary());
    }
}
