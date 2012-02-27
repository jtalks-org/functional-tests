package org.jtalks.tests.jcommune.tests.post;


import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.testng.Assert.*;

/**
 * @autor masyan
 * @autor erik
 */
public class JC17DeleteAnswerInTopic {

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
	public void deleteAnswerInTopicTest() {
		//step 1
		driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//a[@class='button delete']"))
				.click();
		driver.findElement(By.id("jqi_state0_buttonOk")).click();

		//step2
		String lastMessageText = driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']")).getText();

		assertNotEquals(lastMessageText, this.answer);

	}
}
