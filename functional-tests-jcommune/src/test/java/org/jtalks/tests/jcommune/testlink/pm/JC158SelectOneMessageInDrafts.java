package org.jtalks.tests.jcommune.testlink.pm;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.jtalks.tests.jcommune.pages.PMPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsByCssSelector;
import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;

/**
 * @author yacov
 */
public class JC158SelectOneMessageInDrafts extends JCommuneSeleniumTest {
    String title = StringHelp.getRandomString(10);
    String toUser;
    String message = StringHelp.getRandomString(10);


    @BeforeClass(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword", "uUsername2"})
    public void setupCase(String appUrl, String username, String password, String username2) {
        driver.get(appUrl);
        signIn(username, password);

        //create message in drafts
        pmPage.getPmInboxLink().click();
        pmPage.getPmNewMessageLink().click();
        this.toUser = username2;
        pmPage.getToField().sendKeys(toUser);
        pmPage.getTitleField().sendKeys(title);
        pmPage.getMessageField().sendKeys(message);
        pmPage.getSaveButton().click();
        driver.get(appUrl);
    }

    @Test
    public void deleteButtonShouldBeDisabledByDefault() {
        pmPage.getPmInboxLink().click();
        pmPage.getPmDraftsLink().click(); //Enter into PM Drafts
        assertElementExistsBySelector(driver, PMPage.DEL_BUTTON_DISABLED);
    }

    @Test
    public void checkingPmShouldEnableDeleteButtonAndUncheckingShouldDisableIt() throws Exception {
        pmPage.getPmInboxLink().click();
        pmPage.getPmDraftsLink().click();
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click();  //Click on the checkbox of the first message

        assertElementExistsByCssSelector(driver, PMPage.DEL_BUTTON_ENABLED);

        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click(); //Un-check highlighted message
        assertElementExistsBySelector(driver, PMPage.DEL_BUTTON_DISABLED);
    }

    @AfterClass(alwaysRun = true)
    @Parameters({"app-url"})
    public static void destroy(String appUrl) {
        logOut(appUrl);
    }

}
