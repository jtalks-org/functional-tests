package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.PageHtmlValidator.validatePage;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

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
    public void branchPage_Test() throws Exception {
        Topics.w3cGoToBranchPage();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void topicCreatePage_Test() throws Exception {
        Topics.w3cGoToTopicCreatePage();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void codeReviewCreatePage_Test() throws Exception {
        Topics.w3cGoToReviewCreatePage();
        String pageSource = getPageSource();
        validatePage(pageSource);
    }

    @Test
    public void postPage_Test() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topics.createTopic(topic);
        Topics.postAnswer(topic);
        String pageSource = getPageSource();
        validatePage(pageSource);
    }
}
