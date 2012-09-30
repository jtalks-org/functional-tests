package org.jtalks.tests.jcommune.tests.pm;

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
public class JC156SelectOneMessageInInbox extends JCommuneSeleniumTest {

    @BeforeClass(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
    public static void setupCase(String appUrl, String username, String password, String username2, String password2) {
        driver.get(appUrl);
        signIn(username2, password2);
        pmPage.getPmInboxLink().click();
        pmPage.getPmNewMessageLink().click();
        pmPage.getToField().sendKeys(username);
        pmPage.getTitleField().sendKeys(StringHelp.getRandomString(10));
        pmPage.getMessageField().sendKeys(StringHelp.getRandomString(40));
        pmPage.getSendButton().click();
        logOut(appUrl);
        driver.get(appUrl);
        signIn(username, username);
    }

    @Test
    public void deleteButtonShouldBeDisabledByDefault() {
        pmPage.getPmInboxLink().click(); //Enter into PM Inbox
        assertElementExistsBySelector(driver, PMPage.DEL_BUTTON_DISABLED);
    }

    @Test
    public void checkingPmShouldEnableDeleteButtonAndUncheckingShouldDisableIt() throws Exception {
        pmPage.getPmInboxLink().click();
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click();  //Click on the checkbox of the first message

        assertElementExistsByCssSelector(driver, PMPage.DEL_BUTTON_ENABLED);

        pmPage.checkedCheckbox.click(); //Un-check highlighted message
        assertElementExistsBySelector(driver, PMPage.DEL_BUTTON_DISABLED);
    }

    @AfterClass(alwaysRun = true)
    @Parameters({"app-url"})
    public static void destroy(String appUrl) {
        logOut(appUrl);
    }

}
