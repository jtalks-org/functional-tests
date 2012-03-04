package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.TopicCreationPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;


/**
 * Created by IntelliJ IDEA.
 * User: erik
 */
public class JC24CreateTopicWithoutData {
    BranchPage branchPage;
    TopicCreationPage topicCreationPage;

    @BeforeMethod
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        branchPage = new BranchPage(driver);
        topicCreationPage = new TopicCreationPage(driver);
    }

    @AfterMethod
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }
    
    @Test
    public void createTopicWithoutData() throws InterruptedException {
        branchPage.getNewTopicButton().click();
        topicCreationPage.getPostButton().click();
        assertExistBySelector(driver, TopicCreationPage.subjectErrorMessageSel);
        assertExistBySelector(driver, TopicCreationPage.bodyErrorMessageSel);
    }
}
