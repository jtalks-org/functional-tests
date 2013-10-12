package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.utils.TestStringUtils;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.PageHtmlValidator.validatePage;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;

/**
 * @author Leonid Kazancev
 */
public class PagesW3CValidationTest {
    private static String getPageSource() throws InterruptedException {
        Thread.sleep(5000);
        return driver.getPageSource();
    }

    @BeforeMethod
    @Parameters("appUrl")
    public void setup(String appUrl) {
        driver.get("http://autotests.jtalks.org/jcommune/");
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void mainPage_Test() throws Exception {
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void signUpPage_Test() throws Exception {
        mainPage.clickRegistration();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void signInPage_Test() throws Exception {
        mainPage.clickLogin();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void topicPage_Test() throws Exception {
        topicPage.goToTopicPage();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void topicCreatePage_Test() throws Exception {
        topicPage.goToTopicCreatePage();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void codeReviewCreatePage_Test() throws Exception {
        topicPage.goToReviewCreatePage();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void postPage_Test() throws Exception {
        Topic topic = new Topic(TestStringUtils.randomString(40), TestStringUtils.randomString(100));
        String branchTitle = "TestBranch";
        User user = Users.signUp();
        Users.signIn(user);
        topic.withTopicStarter(user);
        Topics.createTopic(topic);
        Topics.postAnswer(topic, branchTitle);
        String pageSource = getPageSource();
        validatePage(pageSource);
    }
}
