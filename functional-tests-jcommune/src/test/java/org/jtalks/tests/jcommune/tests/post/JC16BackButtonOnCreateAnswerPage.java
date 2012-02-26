package org.jtalks.tests.jcommune.tests.post;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * @autor masyan
 */
public class JC16BackButtonOnCreateAnswerPage {

	String answer = StringHelp.getRandomString(20);

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void clickBackButtonOnCreateAnswerPage() {
		//step 1
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/posts/new')]"))
				.click();

		//step 2
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				answer);
		driver.findElement(By.id("back")).click();
		assertNotExistElementOnViewPresent(driver.findElements(
				By.xpath("//li[@class='forum_row']//div[@class='forum_message_cell_text']")), answer);
	}

}
