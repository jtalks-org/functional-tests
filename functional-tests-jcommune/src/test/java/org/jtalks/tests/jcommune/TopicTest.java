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

import net.thucydides.core.annotations.Steps;
import org.jtalks.tests.jcommune.utils.TestStringUtils;
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

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;

/**
 * @author Guram Savinov
 */
public class TopicTest {
    @Steps
    private Users users;
    @Steps
    private Topics topics;

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        users.logOutIfLoggedIn();
    }

    @Test
    public void createTopicWithTitleAndMessage_ShouldPass_JC_13() throws Exception {
        User user = users.signUp();
        users.signIn(user);
        Topic topic = new Topic("subject", "message");
        Topic createdTopic = topics.createTopic(topic);
        Assert.assertTrue(topics.isCreated(createdTopic));
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_SUBJECT_ERROR)
    public void createTopicWithEmptyTitle_ShouldFail_JC_25() throws Exception {
        User user = users.signUp();
        users.signIn(user);
        Topic topic = new Topic("", "message");
        topics.createTopic(topic);
    }

    @Test(enabled = false, expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR)
    public void createTopicWithEmptyMessage_ShouldFail_JC_26() throws Exception {
        User user = users.signUp();
        users.signIn(user);
        Topic topic = new Topic("subject", "");
        topics.createTopic(topic);
    }

    @Test(enabled = false, expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_SUBJECT_ERROR + TopicPage.EMPTY_BODY_ERROR)
    public void createTopicWithoutData_ShouldFail_JC_24() throws Exception {
        User user = users.signUp();
        users.signIn(user);
        Topic topic = new Topic("", "");
        topics.createTopic(topic);
    }

    @Test(enabled = true)
    public void loginAndCreateTopicValidateBranch_ShouldPass() throws Exception {
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic("subject123", "message").withBranch("Micro level");
        topic.withTopicStarter(user);
        topics.createTopic(topic);
        Assert.assertEquals(true, topics.isInCorrectBranch(topic));
    }

    @Test
    public void signUpAndCreateTopicInBranch() throws Exception {
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic("subject123", "message").withBranch("Classical Mechanics");
        topic.withTopicStarter(user);
        topics.createTopic(topic);
    }

    @Test
    public void signUpAndCreateCodeReviewInBranch() throws Exception {
        Topic topic = new Topic("test_code_review1", "SomeCode").withBranch("Acids and Bases");
        User user = users.signUp();
        users.signIn(user);
        topic.withTopicStarter(user);
        topics.createCodeReview(topic);
    }

    @Test
    public void postValidAnswerToTopicShouldSucceed() throws Exception {
        //In this test title of topic variable means subject of post we want to add answer to, and the answer, actually
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic(TestStringUtils.randomString(40), TestStringUtils.randomString(100));
        topic.withTopicStarter(user);
        topics.createTopic(topic);
        topics.postAnswer(topic, topic.getBranch().getTitle());
    }

    @Test(expectedExceptions = PermissionsDeniedException.class)
    public void createTopicAsAnonymousShouldFail() throws Exception {
        Topic topic = new Topic("subject", "message");
        topics.createTopic(topic);
    }

    @Test
    public void createStickedTopic() throws Exception {
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic("subject", "message");
        topic.withTopicStarter(user);
        topic.setSticked(true);
        topics.createTopic(topic);
    }

    @Test
    public void createAnnouncementTopic() throws Exception {
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic("subject", "message");
        topic.withTopicStarter(user);
        topic.setAnnouncement(true);
        topics.createTopic(topic);
    }

    @Test
    public void createTopicWithPoll() throws Exception {
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic("subject", "message");
        topic.withTopicStarter(user);
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        topic.setPoll(poll);
        topics.createTopic(topic);
    }

    @Test
    public void createTopicWithPollEndDate() throws Exception {
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic("subject", "message");
        topic.withTopicStarter(user);
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        poll.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        topic.setPoll(poll);
        topics.createTopic(topic);
    }

    @Test
    public void createTopicWithPollMultipleAnswers() throws Exception {
        User user = User.admin();
        users.signIn(user);
        Topic topic = new Topic("subject", "message");
        topic.withTopicStarter(user);
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        poll.setMultipleAnswers(true);
        topic.setPoll(poll);
        topics.createTopic(topic);
    }

}
