package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * Created by IntelliJ IDEA.
 * User: erik
 */
public class JC24CreateTopicWithoutData {

    TopicPage topicPage;

    @BeforeMethod
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        topicPage = new TopicPage(driver);
    }

    @AfterMethod
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }
    
    @Test
    public void createTopicWithoutData() throws InterruptedException {
        topicPage.getNewButton().click();
        topicPage.getPostButton().click();
        assertExistBySelector(driver, TopicPage.subjectErrorMessageSel);
        assertExistBySelector(driver, TopicPage.bodyErrorMessageSel);
    }
}
