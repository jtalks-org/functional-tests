package org.jtalks.tests.jcommune.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import utils.CollectionHelp;
import utils.DBHelp;
import utils.SeleniumConfig;
import utils.StringHelp;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * Parent class for Tests. Contains common methods
 *
 * @author masyan
 */
public class JCommuneSeleniumTest {
	/**
	 * Object for work with Remote Selenium Server
	 */
	public static WebDriver driver;

	public Connection dbConnection;

	public static final String contextPath = "/test-jcommune";

	/**
	 * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server.
	 * All values are stored in tesng.xml file.
	 *
	 * @param selServerURL  Selenium server URL
	 * @param selDriverType Selenium driver type
	 * @throws MalformedURLException
	 */
	@BeforeSuite(alwaysRun = true)
	@Parameters({"selenium-server-url", "selenium-driver-type", "db-url", "db-driver", "db-username", "db-password", "uUsername", "uEmail", "uUsername2", "uEmail2", "aUsername", "aEmail"})
	public void init(String selServerURL, String selDriverType, String dbURL, String dbDriver, String username, String password, String uUsername,
					 String uEmail, String uUsername2, String uEmail2, String aUsername, String aEmail) throws Exception {
//		driver = new RemoteWebDriver(
//				new URL(selServerURL),
//				SeleniumConfig.getBrowserDriver(selDriverType));
		driver = new FirefoxDriver();
		dbConnection = DBHelp.getConnection(dbURL, dbDriver, username, password);
		DBHelp.setForumUsers(dbConnection, DBHelp.getUsersFromConfigFile(uUsername, uEmail, uUsername2, uEmail2, aUsername, aEmail));
	}

	/**
	 * Method destroy connect with Selenium Server
	 */
	@AfterSuite(alwaysRun = true)
	public void destroy() {
		driver.close();
		if (dbConnection != null) {
			try {
				dbConnection.close();
			}
			catch (SQLException e) {

			}
		}
	}


	/**
	 * This method does the authentication
	 *
	 * @param username
	 * @param password
	 * @author erik
	 */
	public static void signIn(String username, String password) {
		driver.findElement(By.xpath("//a[@href='" + contextPath + "/login']")).click();
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	}


	/**
	 * This method checks that link for logout present, than click on it
	 * Method  used static link because in selenium exist bag  MoveTargetOutOfBoundsError
	 *
	 * @author erik
	 */
	public static void logOut(String appUrl) {
		driver.get(appUrl + "logout");
	}

	/**
	 * Method  select random branch (if exists) and open it
	 */
	public static void clickOnRandomBranch() {
		List<WebElement> webElementsList = driver.findElements(By.xpath("//a[@class='forum_link']"));
		assertNotEmptyCollection(webElementsList);
		CollectionHelp.getRandomWebElementFromCollection(webElementsList).click();
	}


	/**
	 * Method select random section (if exists) and open it
	 */
	public static void clickOnRandomSection() {
		List<WebElement> webElementsList = driver.findElements(By.xpath("//a[@class='forum_header_link']"));
		assertNotEmptyCollection(webElementsList);
		CollectionHelp.getRandomWebElementFromCollection(webElementsList).click();
	}


	/**
	 * Method create topic. Used for create test data. Net checked elements for topic create.
	 */
	public static void createTopicForTest() {
		String subject = StringHelp.getRandomString(20);
		String message = StringHelp.getRandomString(20);

		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]"))
				.click();
		driver.findElement(By.id("subject")).sendKeys(subject);
		driver.findElement(By.id("tbMsg")).sendKeys(message);
		driver.findElement(By.id("post")).click();
	}

	/**
	 * Method create post. Used for create test data. Not checked elements for post create.
	 */
	public static void createAnswerForTest(String answer) {
		//step 1
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/posts/new')]"))
				.click();

		//step 2
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				answer);
		driver.findElement(By.id("post")).click();

	}

	/**
	 * Method return application context path
	 *
	 * @return String. Application context
	 */
	public static String getApplicationContextPath() {
		return contextPath;
	}

}
