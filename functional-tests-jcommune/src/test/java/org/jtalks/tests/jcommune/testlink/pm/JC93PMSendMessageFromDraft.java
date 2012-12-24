package org.jtalks.tests.jcommune.testlink.pm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @author masyan
 */
public class JC93PMSendMessageFromDraft {
    String title = StringHelp.getRandomString(10);
    String toUser;
    String message = StringHelp.getRandomString(10);

    @BeforeMethod(enabled = false)
    @Parameters({"app-url", "uUsername", "uPassword", "uUsername2"})
    public void setupCase(String appUrl, String username, String password, String username2) {
        driver.get(appUrl);
        signIn(username, password);
        pmPage.getPmInboxLink().click();
        pmPage.getPmNewMessageLink().click();
        this.toUser = username2;
        pmPage.getToField().sendKeys(toUser);
        pmPage.getTitleField().sendKeys(title);
        pmPage.getMessageField().sendKeys(message);
        pmPage.getSaveButton().click();
    }

    @AfterMethod(enabled = false)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test(enabled = false)
    public void pmSendMessageFromDraftTest() {
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getDraftMessageCheckboxes()).click();
        pmPage.getDraftMessageEditButton().click();
        pmPage.getSendButton().click();
        assertElementExistsBySelector(driver, pmPage.pmHeadingOutboxSel);
    }
}
