package org.jtalks.tests.jcommune.tests.topic;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * author: erik
 */
public class JC119PaginationForUnregisteredUser {
    @BeforeMethod
    @Parameters({"app-url"})

    public void setUp(String appUrl) {
        driver.get(appUrl);
        clickOnRandomBranch();
    }

    @Test
    @Parameters ("pageSizeForUnregisteredUser")
    public void topicsCountShouldBeFiftyOnFirstPage(int pageSize) {
        int topicsSize = topicPage.getTopicsList().size();
        Assert.assertEquals(topicsSize, pageSize,
                "\nSelected branch: " + branchPage.getBranchName()
                + "\nTopics: " + topicsSize);
    }
}
