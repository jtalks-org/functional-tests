package org.jtalks.tests.jcommune.tests.topic;

import org.testng.annotations.*;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionContainsInString;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * @author masyan
 */
public class JC39EditTopicWithValidMessage {

    @DataProvider(name = "validMessage")
    public Object[][] validMessage() {
        String validMessage = StringHelp.getRandomString(10);
        String maxMessage = StringHelp.getRandomString(20000);

        return new Object[][]{
                {validMessage},
                {maxMessage}
        };
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {

        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        createTopicForTest();
        postPage.getEditTopicButton().click();
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);

    }

    @Test(dataProvider = "validMessage")
    public void editTopicWithValidMessageTest(String msg) {
        topicPage.getMessageField().clear();
        StringHelp.setLongTextValue(driver, topicPage.getMessageField(), msg);
        topicPage.getPostButton().click();

        assertionContainsInString(topicPage.getTopicMessage().getText(), msg);
        assertionContainsInString(topicPage.getTopicMessage().getText(), "edited");
    }
}
