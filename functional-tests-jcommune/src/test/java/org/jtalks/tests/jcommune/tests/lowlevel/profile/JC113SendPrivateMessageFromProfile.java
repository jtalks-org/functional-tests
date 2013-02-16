package org.jtalks.tests.jcommune.tests.lowlevel.profile;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.pmPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;


/**
 * @autor masyan
 */
public class JC113SendPrivateMessageFromProfile {

	String recipient;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String usernameSecond, String passwordSecond) {
		this.recipient = usernameSecond;
		driver.get(appUrl);
		signIn(usernameSecond, passwordSecond);
		clickOnRandomBranch();
		createTopicForTest();
		String url = driver.getCurrentUrl();
		logOut(appUrl);
		signIn(username, password);
		driver.get(url);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);

	}

	@Test
	public void sendPrivateMessageFromProfileTest() {
		CollectionHelp.getFirstWebElementFromCollection(postPage.getAuthorsOfPostsList()).click();

		profilePage.getNewPrivateMessageButton().click();

		pmPage.getMessageField().sendKeys(StringHelp.getRandomString(10));

		pmPage.getTitleField().sendKeys(StringHelp.getRandomString(10));

		pmPage.getSendButton().click();

		String recipientToLastMessage = CollectionHelp.getFirstWebElementFromCollection(pmPage.getRecepientsList()).getText();

		assertEquals(recipientToLastMessage, this.recipient);
	}
}
