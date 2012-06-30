package org.jtalks.tests.jcommune.tests.pm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.pmPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * @author masyan
 */
public class JC92PMSavesNewMessage {
    String title = StringHelp.getRandomString(10);
    String toUser;
    String message = StringHelp.getRandomString(10);

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword", "uUsername2"})
    public void setupCase(String appUrl, String username, String password, String username2) {
        driver.get(appUrl);
        signIn(username, password);
        pmPage.getPmInboxLink().click();
        pmPage.getPmNewMessageLink().click();
        this.toUser = username2;
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test
    public void pmSavesNewMessageTest() {
        pmPage.getToField().sendKeys(toUser);
        pmPage.getTitleField().sendKeys(title);
        pmPage.getMessageField().sendKeys(message);
        pmPage.getSaveButton().click();
        String titleInDraft = CollectionHelp.getFirstWebElementFromCollection(pmPage.getDraftMessageTitles()).getText();
        assertEquals(title, titleInDraft, "Not correct value in Subject field should be '" + title + "' actual='" + titleInDraft + "'");
    }
}
