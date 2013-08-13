package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.utils.StringHelp;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.PageHtmlValidator.validatePage;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;

/**
 * @author Leonid Kazancev
 */
public class PagesW3CValidationTest {
    @BeforeMethod
    @Parameters("appUrl")
    public void setup(String appUrl) {
        driver.get("http://autotests.jtalks.org/jcommune/");
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void mainPage_Test() throws ValidationException {
        String pageSource = driver.getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void signUpPage_Test() throws ValidationException {
        mainPage.clickRegistration();
        String pageSource = driver.getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void signInPage_Test() throws ValidationException {
        mainPage.clickLogin();
        String pageSource = driver.getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void topicPage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        topicPage.goToTopicPage();
        String pageSource = driver.getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void topicCreatePage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        topicPage.goToTopicCreatePage();
        String pageSource = driver.getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void codeReviewCreatePage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        topicPage.goToReviewCreatePage();
        String pageSource = driver.getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void postPage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        Topic topic = new Topic(StringHelp.randomString(40), StringHelp.randomString(100));
        String branchTitle = "TestBranch";
        User user = Users.signUp();
        Users.signIn(user);
        topic.withTopicStarter(user);
        Topics.createTopic(topic);
        Topics.postAnswer(topic, branchTitle);
        String pageSource = driver.getPageSource();
        validatePage(pageSource);
    }
}
