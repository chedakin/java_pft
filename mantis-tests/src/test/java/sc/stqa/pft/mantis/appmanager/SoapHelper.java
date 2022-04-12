package sc.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import sc.stqa.pft.mantis.Models.Issue;
import sc.stqa.pft.mantis.Models.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {

    private final ApplicationManager app;
    private String adminUsername;
    private String adminPassword;
    private String soapURL;

    public SoapHelper(ApplicationManager app) {
        this.app = app;
        adminUsername = app.getProperty("web.adminLogin");
        adminPassword = app.getProperty("web.adminPassword");
        soapURL = app.getProperty("soap.url");
    }

    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        return new MantisConnectLocator().getMantisConnectPort(new URL(soapURL));
    }

    public Set<Project> getProjects() throws RemoteException, MalformedURLException, ServiceException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(adminUsername, adminPassword);
        return Arrays.asList(projects).stream().map((p) -> new Project().withId(p.getId()).withName(p.getName())).collect(Collectors.toSet());
    }



    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        String category = Arrays.stream(mc.mc_project_get_categories(adminUsername, adminPassword, issue.getProject().getId())).iterator().next();

        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(issue.getProject().getId(), issue.getProject().getName()));
        issueData.setCategory(category);

        BigInteger issueId = mc.mc_issue_add(adminUsername, adminPassword, issueData);
        IssueData createdIssueData = mc.mc_issue_get(adminUsername, adminPassword, issueId);

        return new Issue().withId(createdIssueData.getId()).withSummary(createdIssueData.getSummary())
                .withDescription(createdIssueData.getDescription())
                .withProject(new Project().withId(createdIssueData.getProject().getId())
                        .withName(createdIssueData.getProject().getName()));
    }
}
