package org.jtalks.tests.jcommune.tests.pm;

import org.jtalks.tests.jcommune.pages.PMPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC88PMMessageValidationWithValidData {
	PMPage pmPage;

	@DataProvider(name = "validMessage")
	public Object[][] validMessage() {
		String shortString = StringHelp.getRandomString(2);
		String longString = StringHelp.getRandomString(20000);

		return new Object[][]{
				{shortString},
				{longString}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		pmPage = new PMPage(driver);
		pmPage.getPmInboxLink().click();
		pmPage.getPmNewMessageLink().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test(dataProvider = "validMessage")
	public void pmMessageValidationWithValidDataTest(String message) {
		StringHelp.setLongTextValue(driver, pmPage.getMessageField(), message);
		pmPage.getSendButton().click();
		assertNotExistById(driver, pmPage.bodyMsgErrorMessageSel);
	}
}
