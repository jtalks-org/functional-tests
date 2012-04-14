package org.jtalks.tests.jcommune.tests.pm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.pmPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @author masyan
 */
public class JC87PMMessageValidationWithNotValidData {

	@DataProvider(name = "notValidMessage")
	public Object[][] notValidMessage() {
		String empty = "";
		String shortString = StringHelp.getRandomString(1);
		String shortSpStart = " " + StringHelp.getRandomString(1);
		String shortSpEnd = StringHelp.getRandomString(1) + " ";
		String longString = StringHelp.getRandomString(20001);

		return new Object[][]{
				{empty},
				{shortString},
				{shortSpStart},
				{shortSpEnd},
				{longString}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		pmPage.getPmInboxLink().click();
		pmPage.getPmNewMessageLink().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test(dataProvider = "notValidMessage")
	public void pmMessageValidationWithNotValidDataTest(String message) {
		StringHelp.setLongTextValue(driver, pmPage.getMessageField(), message);
		pmPage.getSendButton().click();
		assertionExistById(driver, pmPage.bodyMsgErrorMessageSel);
	}
}
