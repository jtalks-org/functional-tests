package org.jtalks.tests.jcommune.tests.pm;
import org.jtalks.tests.jcommune.assertion.Exsistence;
import org.openqa.selenium.internal.seleniumemulation.GetAttribute;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.pmPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @author yacov
 */
public class JC156SelectOneMessageInInbox {
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
		pmPage.getToField().sendKeys(toUser);
		pmPage.getTitleField().sendKeys(title);
		pmPage.getMessageField().sendKeys(message);
		pmPage.getSaveButton().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}
	@Test
	public void JC156SelectOneMessageInInbox() {
		pmPage.getPmInboxLink().click(); //Enter into PM Inbox
        Exsistence.assertionCheckIfDisabled(driver, pmPage.delButtonSel);   //Control if 'Delete' is disabled
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click();  //Click on the checkbox of the first message
        Exsistence.assertionCheckIfEnabled(driver, pmPage.delButtonSel);  // Control if 'Delete' button is now become enabled
        pmPage.getInboxCheckedCheckbox().click(); //Un-check highlighted message
        Exsistence.assertionCheckIfDisabled(driver, pmPage.delButtonSel);   //Control if 'Delete' button become is disabled
    }
	}
