package org.jtalks.tests.jcommune.tests.pm;

import org.jtalks.tests.jcommune.pages.PMPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;


/**
 * @autor masyan
 */
public class JC83PMRecepientValidationWithNotValidData {
	PMPage pmPage;

	@DataProvider(name = "notValidRecipient")
	public Object[][] notValidRecepient() {
		String empty = "";
		String notExist = StringHelp.getRandomString(12);

		return new Object[][]{
				{empty},
				{notExist}
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

	@Test(dataProvider = "notValidRecipient")
	public void pmRecepientValidationWithNotValidDataTest(String recipient) {
		pmPage.getToField().sendKeys(recipient);
		pmPage.getSendButton().click();
		assertExistById(driver, pmPage.recipientErrorMessageSel);
	}
}
