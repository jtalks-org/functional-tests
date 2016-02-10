/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Poll;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Date;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Guram Savinov
 */
public class TopicTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test(groups = "ui-tests")
    public void createTopicWithTitleAndMessage_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Assert.assertTrue(Topics.isCreated(createdTopic));
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.SUBJECT_SIZE_ERROR)
    public void createTopicWithEmptyTitle_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withTitle("");
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMinTitle_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withTitle("a");
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMaxTitle_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withTitle(randomAlphanumeric(120));
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.SUBJECT_SIZE_ERROR)
    public void createTopicExceedingMaxTitle_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withTitle(randomAlphanumeric(121));
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", enabled = false, expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR)
    public void createTopicWithEmptyMessage_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withBody("");
        Topics.createTopic(topic);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR)
    public void createTopicWithOneSymbolInMessage_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withBody("a");
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMinMessage_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withBody("ab");
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMaxMessage_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withBody(randomAlphanumeric(20000));
        Topics.createTopic(topic);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR)
    public void createTopicExceedingMaxMessage_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withBody(randomAlphanumeric(20001));
        Topics.createTopic(topic);
    }

    @Test(enabled = false, expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.SUBJECT_SIZE_ERROR + TopicPage.EMPTY_BODY_ERROR)
    public void createTopicWithoutData_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic().withTitle("").withBody("");
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests")
    public void createTopicInBranch_mustPass() throws Exception {
        User user = User.admin();
        Users.signIn(user);
        Topic topic = new Topic().withBranch("Cricet");
        topic.withTopicStarter(user);
        Topics.createTopic(topic);
        Assert.assertEquals(true, Topics.isInCorrectBranch(topic));
    }

    @Test(groups = "ui-tests")
    public void postValidAnswerToTopic_ShouldPass() throws Exception {
        //In this test title of topic variable means subject of post we want to add answer to, and the answer, actually
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topics.createTopic(topic);
        Topics.postAnswer(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = PermissionsDeniedException.class)
    public void createTopicAsAnonymous_ShouldFail() throws Exception {
        Topic topic = new Topic();
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests")
    public void createStickedTopic_ShouldPass() throws Exception {
        User user = User.admin();
        Users.signIn(user);
        Topic topic = new Topic();
        topic.withTopicStarter(user);
        topic.setSticked(true);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests")
    public void createAnnouncementTopic_ShouldPass() throws Exception {
        User user = User.admin();
        Users.signIn(user);
        Topic topic = new Topic();
        topic.withTopicStarter(user);
        topic.setAnnouncement(true);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests")
    public void createTopicWithPoll_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title");
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_SUBJECT_EMPTY_ERROR)
    public void createTopicWithoutPollSubject_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("");
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_SUBJECT_SIZE_ERROR)
    public void createTopicWithInsufficientPollSubject_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("ab");
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMinPollSubject_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("abc");
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMaxPollSubject_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll(randomAlphanumeric(120));
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_SUBJECT_SIZE_ERROR)
    public void createTopicWithExcessivePollSubject_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll(randomAlphanumeric(121));
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_OPTIONS_ERROR)
    public void createTopicWithoutPollOptions_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title", new String[]{""});
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests")
    public void createTopicWithMinPollOptionsLengthAndNumber_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title", new String[]{"1", "2"});
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMaxPollOptionsLength_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title", new String[]{"option1", randomAlphanumeric(50)});
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_OPTIONS_LENGTH_ERROR)
    public void createTopicWithExcessivePollOptionsLength_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title", new String[]{"option1", randomAlphanumeric(51)});
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_DUPLICATES_ERROR)
    public void createTopicWithDuplicatedPollOptions_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title", new String[]{"option1", "option1"});
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_OPTIONS_NUMBER_ERROR)
    public void createTopicWithOnlyPollOption_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title", new String[]{"option1"});
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test
    public void createTopicWithMaxPollOptionsNumber_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title").withNumberOfOptions(50);
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.POLL_OPTIONS_NUMBER_ERROR)
    public void createTopicWithExcessivePollOptionsNumber_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title").withNumberOfOptions(51);
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }


    @Test(groups = "ui-tests")
    public void createTopicWithPollEndDate_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title");
        poll.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

    @Test(groups = "ui-tests")
    public void createTopicWithPollMultipleAnswers_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Poll poll = new Poll("poll title");
        poll.setMultipleAnswers(true);
        topic.setPoll(poll);
        Topics.createTopic(topic);
    }

}
