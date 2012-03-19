package org.jtalks.tests.jcommune.tests.pm;

import org.jtalks.tests.jcommune.pages.PMPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * @author masyan
 */
public class JC93PMSendMessageFromDraft {
	PMPage pmPage;
	String title = StringHelp.getRandomString(10);
	String toUser;
	String message = StringHelp.getRandomString(10);

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2"})
	public void setupCase(String appUrl, String username, String password, String username2) {
		driver.get(appUrl);
		signIn(username, password);
		pmPage = new PMPage(driver);
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
	public void pmSendMessageFromDraftTest() {
		CollectionHelp.getFirstWebElementFromCollection(pmPage.getDraftMessageEditButtons()).click();
		pmPage.getSendButton().click();
		assertExistBySelector(driver, pmPage.pmHeadingOutboxSel);
	}
}
