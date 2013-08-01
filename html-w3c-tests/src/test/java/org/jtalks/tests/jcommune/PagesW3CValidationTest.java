package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.PageHtmlValidator.validatePage;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.action.Topics.openTopicInCurrentBranch;
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
    public void signUpPage_Test() throws ValidationException {
        mainPage.clickRegistration();
        validatePage();
    }

    @Test
    public void signInPage_Test() throws ValidationException {
        mainPage.clickRegistration();
        validatePage();
    }

    @Test
    public void topicPage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        Users.signIn(Users.signUp());
        Branches.openBranch("TestBranch");
        validatePage();
    }

    @Test
    public void topicCreatePage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        Users.signIn(Users.signUp());

        Topic topic = new Topic("subject123", "New final test answer");
        if (topic.getBranch() == null) {
            Branch branch = new Branch(branchPage.getBranchList().get(0).getText());
            topic.withBranch(branch);
        }
        Branches.openBranch(topic.getBranch().getTitle());
        topicPage.getNewButton().click();
        validatePage();
    }

    @Test
    public void codeReviewCreatePage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        Users.signIn(Users.signUp());

        Topic topic = new Topic("subject123", "New final test answer");
        if (topic.getBranch() == null) {
            Branch branch = new Branch(branchPage.getBranchList().get(0).getText());
            topic.withBranch(branch);
        }
        Branches.openBranch(topic.getBranch().getTitle());
        topicPage.getNewCodeReviewButton().click();
        validatePage();
    }

    @Test
    public void postPage_Test() throws ValidationException, PermissionsDeniedException, InterruptedException {
        Topic topic = new Topic("subject123", "New final test answer");
        String branchTitle = "TestBranch";
        Users.signIn(Users.signUp());
        Topics.postAnswer(topic, branchTitle);
        validatePage();
    }

    @Test
    public void sectionPage_Test() throws ValidationException {
        mainPage.clickRegistration();
        validatePage();
    }

    @Test
    public void branchPage_Test() throws ValidationException {
        validatePage();
    }

    @Test
    public void pmPage_Test() throws ValidationException {
        mainPage.clickRegistration();
        validatePage();
    }

    @Test
    public void profilePage_Test() throws ValidationException {
        mainPage.clickRegistration();
        validatePage();
    }
}
