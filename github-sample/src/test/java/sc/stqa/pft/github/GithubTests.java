package sc.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

    @Test
    public void testCommit() throws IOException {
        Github github = new RtGithub("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");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("chedakin", "java_pft")).commits();

        for(RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }

    }
}
