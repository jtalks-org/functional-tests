package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Notifications;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReview;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReviewComment;
import org.jtalks.tests.jcommune.webdriver.entity.topic.QA;
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
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.getAppUrl;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
/**
 *  @author timisoreana 23.03.2015
 */
public class MoveTopicsPermissionTest {


    @BeforeMethod(alwaysRun = false)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void MoveTopicToOtherBranch_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withTopicStarter(user));
        Users.logout();

        User userTopicMover = new User();
        Users.signIn(userTopicMover);

        Topics.moveTopic(topic, "Classical Mechanics");
        Assert.assertEquals("Classical Mechanics", topic.getBranch().getTitle());
    }

    @Test // ( expectedExceptions = PermissionsDeniedException.class,
            //expectedExceptionsMessageRegExp = Page.ERROR)
    public void UserWithoutMoveTopicsPermissionAndWithVewTopic_MoveTopic_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withTopicStarter(user));

        User userWithoutMoveAndWithViewTopicPerm = Users.signUpAndSignIn();
        Topics.moveByUser(topic, userWithoutMoveAndWithViewTopicPerm);
    }



    @Test //( expectedExceptions = PermissionsDeniedException.class,
          //  expectedExceptionsMessageRegExp = Page.ERROR)
    public void UserWithMoveTopicsAndWithoutViewTopic_MoveTopic_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withTopicStarter(user));

        User userWithMoveAndWithoutPerm = Users.signUpAndSignIn();
        Topics.moveByUser(topic, userWithMoveAndWithoutPerm);
    }


    @Test //( expectedExceptions = PermissionsDeniedException.class,
    //  expectedExceptionsMessageRegExp = Page.ERROR)
    public void UserWithoutMoveTopicsAndWithoutViewTopic_MoveTopic_ShouldFail() throws Exception {

        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withTopicStarter(user));

        User userWithoutMoveAndWithoutPerm = Users.signUpAndSignIn();
        Topics.moveByUser(topic, userWithoutMoveAndWithoutPerm);
    }


    @Test //( expectedExceptions = PermissionsDeniedException.class,
    //  expectedExceptionsMessageRegExp = Page.ERROR)
    public void AnonymousUser_MoveTopic_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withTopicStarter(user));
        Users.logout();

        Topics.moveByUser(topic, user);
    }

    @Test
    public void User_MoveCodeReviewTopic_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview();
        Topics.createCodeReview(codeReview);

        User user = Users.signUpAndSignIn();
        Topics.moveByUser(codeReview, user);
    }

    @Test
    public void User_MoveQATopic_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        QA qa = new QA();
        Topics.createQA(qa,user);
        Users.logout();

        User mover = Users.signUpAndSignIn();
        Topics.moveByUser(qa, mover);
    }

    @Test
    public void TopicsURLIsNotChangedAfterMove_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic());
        String s = JCommuneSeleniumConfig.driver.getCurrentUrl();
        Topics.moveByUser(topic, user);

        Assert.assertEquals(JCommuneSeleniumConfig.driver.getCurrentUrl(), s);
    }


}