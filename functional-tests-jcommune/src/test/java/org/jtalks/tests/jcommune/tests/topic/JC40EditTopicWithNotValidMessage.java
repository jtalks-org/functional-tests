package org.jtalks.tests.jcommune.tests.topic;

import org.testng.annotations.*;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @author masyan
 */
public class JC40EditTopicWithNotValidMessage {

    @DataProvider(name = "notValidMessage")
    public Object[][] notValidMessage() {
        String shortMsg = StringHelp.getRandomString(1);
        String startSpMsg = " " + StringHelp.getRandomString(1);
        String endSpMsg = StringHelp.getRandomString(1) + " ";
        String longMsg = StringHelp.getRandomString(20001);
        return new Object[][]{
                {shortMsg},
                {startSpMsg},
                {endSpMsg},
                {longMsg}

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

    @Test(dataProvider = "notValidMessage")
    public void editTopicWithNotValidMessageTest(String msg) {

        topicPage.getMessageField().clear();
        StringHelp.setLongTextValue(driver, topicPage.getMessageField(), msg);
        topicPage.getPostButton().click();

        assertElementExistsBySelector(driver, topicPage.bodyErrorMessageSel);
    }

}
