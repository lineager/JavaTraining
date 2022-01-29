package ru.stqa.pft.restsample.tests;

import org.apache.http.client.fluent.Request;
import org.testng.SkipException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import ru.stqa.pft.restsample.model.Issue;
import java.util.Set;
import java.io.IOException;

import org.apache.http.client.fluent.Executor;

public class TestBase {
    //вынес в отдельный модуль
    public void skipIfNotFixed(int issueId) throws IOException{
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    public boolean isIssueOpen(int issueId) throws IOException {
        String id = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId))).returnContent().asString();
        JsonElement parsed = new JsonParser().parse(id);
        JsonElement jsonIssues = parsed.getAsJsonObject().get("issues");
        Set<Issue> issues = new Gson().fromJson(jsonIssues, new TypeToken<Set<Issue>>() {}.getType());
        Issue selectedIssue = issues.iterator().next();

        if(selectedIssue.getStatus().equals("Resolved") || selectedIssue.getStatus().equals("Closed")) {
            return false;
        } else {
            return true;
        }
    }

    private Executor getExecutor() {
        return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
    }
}

