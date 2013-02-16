package org.jtalks.tests.jcommune.tests.lowlevel.pm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.testng.Assert.assertEquals;

/**
 * @author masyan
 */
public class JC82SendPrivateMessage {
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

    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test
    public void sendPrivateMessageTest() {
        //step 1
        String subject = StringHelp.getRandomString(10);
        pmPage.getPmInboxLink().click();
        pmPage.getPmNewMessageLink().click();
        pmPage.getToField().sendKeys(username2);
        pmPage.getTitleField().sendKeys(subject);
        pmPage.getMessageField().sendKeys(StringHelp.getRandomString(10));
        pmPage.getSendButton().click();
        logOut(appUrl);

        //step 2
        signIn(this.username2, this.username2);
        pmPage.getPmInboxLink().click();
        String textLink = CollectionHelp.getFirstWebElementFromCollection(pmPage.getPmSubjectLinks()).getText();

        assertEquals(subject, textLink);
    }
}
