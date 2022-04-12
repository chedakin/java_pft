package sc.stqa.pft.mantis.Tests;

import org.testng.annotations.Test;
import sc.stqa.pft.mantis.Models.Issue;
import sc.stqa.pft.mantis.Models.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class SoapTests extends TestBase {

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        skipIfNotFixed(BigInteger.valueOf(2));

        Set<Project> projects = app.soap().getProjects();
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project);
        }
    }

    @Test
    public void testCreateIssue() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();

        Issue issue = new Issue().withSummary("Test Issue").withDescription("Issue, which was created automatically")
                .withProject(projects.iterator().next());

        Issue created = app.soap().addIssue(issue);

        assertEquals(issue.getSummary(), created.getSummary());
    }




}
