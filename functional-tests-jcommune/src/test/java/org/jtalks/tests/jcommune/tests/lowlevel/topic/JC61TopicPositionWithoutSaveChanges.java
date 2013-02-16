package org.jtalks.tests.jcommune.tests.lowlevel.topic;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.testng.Assert.assertNotEquals;

/**
 * @author masyan
 */
public class JC61TopicPositionWithoutSaveChanges {
    String editedSubject;

    @BeforeMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        //create 2 topics
        createTopicForTest();
        branchPage.getBreadCrumbsBranchLink().click();
        createTopicForTest();
        branchPage.getBreadCrumbsBranchLink().click();

        CollectionHelp.getWebElementFromCollectionByIndex(topicPage.getTopicsList(), 2).click();

    }

    @AfterMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);

    }

    @Test(enabled = false)
    public void topicPositionWithoutSaveChangesTest() {
        //first step
        editedSubject = topicPage.getTopicSubject().getText();
        postPage.getEditTopicButton().click();
        topicPage.getMessageField().clear();
        topicPage.getMessageField().sendKeys(StringHelp.getRandomString(10));
        topicPage.getBackButtonOnEditForm().click();
        branchPage.getBreadCrumbsBranchLink().click();

        //second step
        CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicsList()).click();
        assertNotEquals(topicPage.getTopicSubject().getText(), editedSubject);

    }
}
