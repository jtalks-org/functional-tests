package org.jtalks.tests.jcommune.tests.lowlevel.pm;

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
import static org.testng.Assert.assertNotEquals;

/**
 * @author masyan
 */
public class JC89CounterUnreadMessagesChanges {
	String prevCount;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String username2, String password2) {
		driver.get(appUrl);
		signIn(username, password);
		pmPage.getPmInboxLink().click();
		pmPage.getPmNewMessageLink().click();
		pmPage.getToField().sendKeys(username2);
		pmPage.getTitleField().sendKeys(StringHelp.getRandomString(10));
		pmPage.getMessageField().sendKeys(StringHelp.getRandomString(10));
		pmPage.getSendButton().click();
		logOut(appUrl);
		signIn(username2, password2);
		String counterString = pmPage.getPmInboxLink().getText();
		prevCount = counterString.substring(9, counterString.length() - 1);

		pmPage.getPmInboxLink().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void counterUnreadMessagesChangesTest() {

		CollectionHelp.getFirstWebElementFromCollection(pmPage.getPmSubjectLinks()).click();
		String counterString = pmPage.getPmInboxLink().getText();
		String postCount = counterString.substring(9, counterString.length() - 1);

		assertNotEquals(postCount, prevCount);
	}
}
