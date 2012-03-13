package org.jtalks.tests.jcommune.common;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.LogOutPage;
import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.PMPage;
import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.SectionPage;
import org.jtalks.tests.jcommune.pages.SignInPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
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

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotEmptyCollection;

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


	//Pages for common methods
	static MainPage mainPage;

	static SignInPage signInPage;

	static TopicPage topicPage;

	static PostPage postPage;

	static SectionPage sectionPage;

	static BranchPage branchPage;

	static LogOutPage logOutPage;

	static PMPage pmPage;


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
		driver = new RemoteWebDriver(
				new URL(selServerURL),
				SeleniumConfig.getBrowserDriver(selDriverType));
//		driver = new FirefoxDriver();
		dbConnection = DBHelp.getConnection(dbURL, dbDriver, username, password);
		DBHelp.setForumUsers(dbConnection, DBHelp.getUsersFromConfigFile(uUsername, uEmail, uUsername2, uEmail2, aUsername, aEmail));

		mainPage = new MainPage(driver);
		signInPage = new SignInPage(driver);
		topicPage = new TopicPage(driver);
		postPage = new PostPage(driver);
		sectionPage = new SectionPage(driver);
		branchPage = new BranchPage(driver);
		logOutPage = new LogOutPage(driver);
		pmPage = new PMPage(driver);
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
		mainPage.getLoginLink().click();
		signInPage.getUsernameField().sendKeys(username);
		signInPage.getPasswordField().sendKeys(password);
		signInPage.getSubmitButton().click();
	}


	/**
	 * This method checks that link for logout present, than click on it
	 * Method  used static link because in selenium exist bag  MoveTargetOutOfBoundsError
	 *
	 * @author erik
	 */
	public static void logOut(String appUrl) {
		logOutPage.getLogOutButton().click();
	}

	/**
	 * Method  select random branch (if exists) and open it
	 *
	 * @return Selected branch
	 */
	public static WebElement clickOnRandomBranch() {
		List<WebElement> webElementsList = branchPage.getBranchList();
		assertNotEmptyCollection(webElementsList);
		WebElement branch = CollectionHelp.getRandomWebElementFromCollection(webElementsList);
		branch.click();
		return branch;
	}


	/**
	 * Method select random section (if exists) and open it
	 */
	public static void clickOnRandomSection() {
		List<WebElement> webElementsList = sectionPage.getSectionList();
		assertNotEmptyCollection(webElementsList);
		CollectionHelp.getRandomWebElementFromCollection(webElementsList).click();
	}

	/**
	 * Method select random topic (if exists) and open it
	 */
	public static void clickOnRandomTopic() {
		List<WebElement> webElementsList = topicPage.getTopicsList();
		assertNotEmptyCollection(webElementsList);
		CollectionHelp.getRandomWebElementFromCollection(webElementsList).click();
	}


	/**
	 * Method create topic. Used for create test data. Net checked elements for topic create.
	 */
	public static void createTopicForTest() {
		String subject = StringHelp.getRandomString(20);
		String message = StringHelp.getRandomString(20);

		topicPage.getNewButton().click();
		topicPage.getSubjectField().sendKeys(subject);
		topicPage.getMessageField().sendKeys(message);
		topicPage.getPostButton().click();
	}

	/**
	 * Method create post. Used for create test data. Not checked elements for post create.
	 */
	public static void createAnswerForTest(String answer) {
		//step 1
		postPage.getNewButton().click();

		//step 2
		StringHelp.setLongTextValue(driver, postPage.getMessageField(), answer);
		postPage.getPostButton().click();

	}

	/**
	 * Method send private message.    Message sended from current user.
	 */
	public static void sendPrivateMessageForTest(String to, String subject, String msg) {
		pmPage.getPmInboxLink().click();
		pmPage.getPmNewMessageLink().click();
		pmPage.getToField().sendKeys(to);
		pmPage.getTitleField().sendKeys(subject);
		pmPage.getMessageField().sendKeys(msg);
		pmPage.getSendButton().click();
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
