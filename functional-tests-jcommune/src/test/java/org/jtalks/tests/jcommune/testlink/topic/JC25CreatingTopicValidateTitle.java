package org.jtalks.tests.jcommune.testlink.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @author erik
 */
public class JC25CreatingTopicValidateTitle {
    String shortTitle = "mssg";
    String validTitle = "new topic";

    @BeforeMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
    }

    @AfterMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test(alwaysRun = true, enabled = false)
    public void testValidateTitle() {
        topicPage.getNewButton().click();
        topicPage.getSubjectField().sendKeys(shortTitle);
        topicPage.getPostButton().click();

        assertElementExistsBySelector(driver, TopicPage.subjectErrorMessageSel);

        topicPage.getSubjectField().clear();
        topicPage.getSubjectField().sendKeys(validTitle);
        topicPage.getPostButton().click();

        assertionNotExistBySelector(driver, TopicPage.subjectErrorMessageSel);


    }


}
