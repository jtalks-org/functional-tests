package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
/**
 *  @author timisoreana 23.03.2015
 */
public class MoveTopicsPermission {


    @BeforeMethod(alwaysRun = false)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void MoveTopicToOtherBranch_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withBranch("Micro level");
        Topic createdTopic = Topics.createTopic(topic);
        Users.logout();
        User userTopicMover = new User("mover", "mover","mover@jtalks.org");
        Users.signIn(userTopicMover);
        Topics.moveByTopicMover(userTopicMover, createdTopic, "Classical Mechanics"); //(userTopicMover, topic, new branch)
        Assert.assertEquals(true, Topics.isInCorrectBranch(topic));

    }

}
