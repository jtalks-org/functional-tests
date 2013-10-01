package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.testng.Assert.assertTrue;

/**
 * @author Andrei Alikov
 */
public class LastReadPostTest {
    private static final Logger logger = LoggerFactory.getLogger(LastReadPostTest.class);

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void createdTopicShouldBeShowedAsNotReadToAnotherUser() throws Exception {
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());
        mainPage.clickLogout();

        Users.signUpAndSignIn();
        Topic topicWithNewMessages = Topics.findTopic(newTopic.getBranch().getTitle(), newTopic.getTitle());

        assertTrue(topicWithNewMessages.hasNewMessages());
    }
}
