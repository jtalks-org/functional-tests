package org.jtalks.tests.jcommune.testlink.topic;

import org.testng.annotations.*;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * @author masyan
 */
public class JC38EditTopicWithNotValidTitle {

    @DataProvider(name = "notValidTitle")
    public Object[][] notValidTitle() {
        String shortTitle = StringHelp.getRandomString(4);
        String startSpTitle = " " + StringHelp.getRandomString(4);
        String endSpTitle = StringHelp.getRandomString(4) + " ";
        return new Object[][]{
                {shortTitle},
                {startSpTitle},
                {endSpTitle}

        };
    }

    @BeforeMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        createTopicForTest();
        postPage.getEditTopicButton().click();
    }

    @AfterMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);

    }

    @Test(dataProvider = "notValidTitle", enabled = false)
    public void editTopicWithNotValidTitleTest(String title) {

        topicPage.getSubjectField().clear();
        topicPage.getSubjectField().sendKeys(title);
        topicPage.getPostButton().click();

        assertElementExistsBySelector(driver, topicPage.subjectErrorMessageSel);
    }

}
