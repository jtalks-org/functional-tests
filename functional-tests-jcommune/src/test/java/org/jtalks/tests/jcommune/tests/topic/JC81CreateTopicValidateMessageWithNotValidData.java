package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * User: erik
 * Date: 18.03.12
 */
public class JC81CreateTopicValidateMessageWithNotValidData {
    TopicPage topicPage;

    @DataProvider(name = "notValidTopic")
    public Object[][] validTopic(){
        String newTopic = "new topic";
        String shortMessage = "m";
        String longTopicMessage = StringHelp.getRandomString(20001);
        return new Object[][] {
                {newTopic, shortMessage, longTopicMessage}
        };
    }

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

    @Test(dataProvider = "notValidTopic")
    public void testValidateMassage(String newTopic, String shortMessage, String longTopicMessage) {
        topicPage.getNewButton().click();
        topicPage.getSubjectField().sendKeys(newTopic);
        topicPage.getMessageField().sendKeys(shortMessage);
        topicPage.getPostButton().click();

        assertExistBySelector(driver, TopicPage.bodyErrorMessageSel);

		topicPage.getMessageField().clear();
		StringHelp.setLongTextValue(driver, topicPage.getMessageField(), longTopicMessage);
		topicPage.getPostButton().click();

		assertExistBySelector(driver, TopicPage.bodyErrorMessageSel);
        Assert.assertEquals(topicPage.getMessageField().getText().trim(), longTopicMessage);

    }
}
