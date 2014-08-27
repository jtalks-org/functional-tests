package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReview;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReviewComment;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Andrey Pancheshenko 27.08.14.
 */
public class CodeReviewTest {

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void signUpAndCreateCodeReviewInBranch() throws Exception {
        CodeReview codeReview = new CodeReview().withBranch("Acids and Bases");
        User user = Users.signUpAndSignIn();
        codeReview.withTopicStarter(user);
        Topics.createCodeReview(codeReview);
    }

    @Test
    public void signUpCreateValidCodeReviewAndLeaveComment_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview();
        codeReview.withLinesNumber(5);
        CodeReviewComment codeReviewComment = new CodeReviewComment();
        codeReviewComment.setLineNumber(3);
        Topics.createCodeReview(codeReview);
        Topics.createCodeReviewComment(codeReview, codeReviewComment);
    }


}
