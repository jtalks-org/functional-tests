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
import static org.testng.Assert.assertEquals;

/**
 * @author masyan
 */
public class JC90PMReplyToNewMessage {

	String title = StringHelp.getRandomString(10);
	String fromUser;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String username2, String password2) {
		driver.get(appUrl);
		signIn(username, password);
		pmPage.getPmInboxLink().click();
		pmPage.getPmNewMessageLink().click();
		pmPage.getToField().sendKeys(username2);
		pmPage.getTitleField().sendKeys(title);
		pmPage.getMessageField().sendKeys(StringHelp.getRandomString(10));
		pmPage.getSendButton().click();
		logOut(appUrl);
		signIn(username2, password2);
		pmPage.getPmInboxLink().click();
		fromUser = username;
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void pmReplyToNewMessageTest() {

		CollectionHelp.getFirstWebElementFromCollection(pmPage.getPmSubjectLinks()).click();
		pmPage.getReplyButton().click();
		String toFieldText = pmPage.getToField().getAttribute("value");
		String subjectFieldText = pmPage.getTitleField().getAttribute("value");
		//check To field
		assertEquals(toFieldText, fromUser, "Not correct value in To field should be '" + fromUser + "' actual='" + toFieldText + "'");
		//check title
		assertEquals(subjectFieldText, "Re: " + title, "Not correct value in Subject field should be '" + "Re: " + title + "' actual='" + subjectFieldText + "'");
	}
}
