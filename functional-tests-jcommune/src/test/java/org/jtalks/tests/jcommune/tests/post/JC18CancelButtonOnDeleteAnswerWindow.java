package org.jtalks.tests.jcommune.tests.post;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.testng.Assert.assertEquals;

/**
 * @autor masyan
 */
public class JC18CancelButtonOnDeleteAnswerWindow {

	String answer = StringHelp.getRandomString(20);

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		createAnswerForTest(this.answer);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void cancelButtonOnDeleteAnswerWindowTest() {
		//step 1
		driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//a[@class='button delete']"))
				.click();
		driver.findElement(By.id("jqi_state0_buttonCancel")).click();

		//step2
		String lastMessageText = driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']")).getText();

		assertEquals(lastMessageText, this.answer);

	}
}
