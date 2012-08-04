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
    public void paginationTest() {
        int topicsSize = topicPage.getTopicsList().size();
        if (topicsSize != 50) {
            Assert.fail("Count of topics not equals 50.\nSelected branch: " + branchPage.getBranchName()
            + "\nTopics: " + topicsSize);
        }
    }
}
