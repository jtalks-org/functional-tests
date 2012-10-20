package org.jtalks.tests.jcommune.tests.pm;

import org.jtalks.tests.jcommune.assertion.Existance;
import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.pages.PMPage.PM_CHECKED_UNREAD_CHECKBOX;
import static org.jtalks.tests.jcommune.pages.PMPage.PM_DEL_DIALOG;

/**
 * @author yacov
 */
public class JC173deleteSeveralMessagesInDrafts extends JCommuneSeleniumTest {
    String username;
    String password;
    String username2;
    String password2;
    String appUrl;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
    public void setupCase(String appUrl, String username, String password, String username2, String password2) {
        driver.get(appUrl);
        signIn(username, password);
        this.username = username;
        this.password = password;
        this.username2 = username2;
        this.password2 = password2;
        this.appUrl = appUrl;
        String subject = StringHelp.getRandomString(10);
        pmPage.getPmInboxLink().click();
        pmPage.getPmNewMessageLink().click();
        pmPage.getToField().sendKeys(username2);
        pmPage.getTitleField().sendKeys(subject);
        pmPage.getMessageField().sendKeys(StringHelp.getRandomString(10));
        pmPage.getSaveButton().click();
        pmPage.getPmInboxLink().click();
        pmPage.getPmNewMessageLink().click();
        pmPage.getToField().sendKeys(username2);
        pmPage.getTitleField().sendKeys(subject);
        pmPage.getMessageField().sendKeys(StringHelp.getRandomString(10));
        pmPage.getSaveButton().click();
    }

    @Test
    public void deleteSeveralMessagesInDrafts() {
        pmPage.getPmInboxLink().click();
        pmPage.getPmDraftsLink().click();
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click();  //Click on the checkbox of the first message
        CollectionHelp.getLastWebElementFromCollection(pmPage.getInboxCheckboxes()).click();
        pmPage.getDelButton().click();  //Click on 'Delete' button
        assertElementExistsBySelector(driver, PM_DEL_DIALOG);      //Control that Alert Message appears
        pmPage.getPmDelMesButtonOK().click(); //Click on 'OK' button of Alert Message
        Existance.assertionNotExistBySelector(driver, PM_CHECKED_UNREAD_CHECKBOX); //Control, that there is no more checked messages on Inbox

    }


    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }


}
