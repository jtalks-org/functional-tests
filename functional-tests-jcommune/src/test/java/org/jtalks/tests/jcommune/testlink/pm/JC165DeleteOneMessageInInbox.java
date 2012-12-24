package org.jtalks.tests.jcommune.testlink.pm;

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
public class JC165DeleteOneMessageInInbox extends JCommuneSeleniumTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
    public void setupCase(String appUrl, String username, String password, String username2, String password2) {
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
        signIn(username, password);
    }

    @Test
    public void deleteMessageInInbox() {
        pmPage.getPmInboxLink().click();
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click();  //Click on the checkbox of the first message
        pmPage.getDelButton().click();  //Click on 'Delete' button
        assertElementExistsBySelector(driver, PM_DEL_DIALOG);   //Control that Alert Message appears
        pmPage.getPmDelMesButtonCancel().click();   //Click on 'Cancel'
        pmPage.assertOneOfPmMessagesIsChecked(driver);

        pmPage.getDelButton().click();   //Click again on 'Delete' button
        assertElementExistsBySelector(driver, PM_DEL_DIALOG);      //Control that Alert Message appears
        pmPage.getPmDelMesButtonOK().click(); //Click on 'OK' button of Alert Message
        Existance.assertionNotExistBySelector(driver, PM_CHECKED_UNREAD_CHECKBOX); //Control, that there is no more checked message on Inbox

    }


    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }


}
