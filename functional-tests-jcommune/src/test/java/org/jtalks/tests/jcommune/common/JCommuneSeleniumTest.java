package org.jtalks.tests.jcommune.common;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.LogOutPage;
import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.PMPage;
import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.jtalks.tests.jcommune.pages.SectionPage;
import org.jtalks.tests.jcommune.pages.SignInPage;
import org.jtalks.tests.jcommune.pages.SignUpPage;
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
import java.util.concurrent.TimeUnit;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotEmptyCollection;

/**
 * Parent class for Tests. Contains common methods
 *
 * @author masyan
 */
public class JCommuneSeleniumTest {
	/**
	 * Object for work with Remote Selenium Server
	 */
	public static WebDriver driver = null;

	//Second driver, if need two browsers
	public static WebDriver driver2 = null;

	public Connection dbConnection;

	public static final String contextPath = "/test-jcommune";

	public static String selServerURL;

	public static String selDriverType;


	//Pages for common methods
	public static MainPage mainPage;

	public static SignInPage signInPage;

	public static SignUpPage signUpPage;

	public static TopicPage topicPage;

	public static PostPage postPage;

	public static SectionPage sectionPage;

	public static BranchPage branchPage;

	public static LogOutPage logOutPage;

	public static PMPage pmPage;

	public static ProfilePage profilePage;

	// Constants to logger
	public static final String beforeTestLog = "Setup test";

	public static final String runTestLog = "Run test";

	public static final String afterTestLog = "Destroy test";

	public static final String elNotFoundLog = "Element not found";

	public static final String elementsNotFoundLog = "Elements not found";

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

		this.selDriverType = selDriverType;

		this.selServerURL = selServerURL;

		recreateDriver(false);

		dbConnection = DBHelp.getConnection(dbURL, dbDriver, username, password);
		DBHelp.setForumUsers(dbConnection, DBHelp.getUsersFromConfigFile(uUsername, uEmail, uUsername2, uEmail2, aUsername, aEmail));

		createPages();
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
	 * This method does the authentication
	 *
	 * @param username
	 * @param password
	 * @author masyan
	 */
	public static void signInByAnotherDriver(String username, String password) {
		if (driver2 != null) {
			MainPage mainPage = new MainPage(driver2);
			SignInPage signInPage = new SignInPage(driver2);
			mainPage.getLoginLink().click();
			signInPage.getUsernameField().sendKeys(username);
			signInPage.getPasswordField().sendKeys(password);
			signInPage.getSubmitButton().click();
		}
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
	 * This method checks that link for logout present, than click on it
	 * Method  used static link because in selenium exist bag  MoveTargetOutOfBoundsError
	 *
	 * @author masyan
	 */
	public static void logOutByAnotherDriver() {
		if (driver2 != null) {
			LogOutPage logOutPage = new LogOutPage(driver2);
			logOutPage.getLogOutButton().click();
		}
	}

	/**
	 * Method  select random branch (if exists) and open it
	 *
	 * @return Selected branch
	 */
	public static WebElement clickOnRandomBranch() {
		List<WebElement> webElementsList = branchPage.getBranchList();
		assertionNotEmptyCollection(webElementsList);
		WebElement branch = CollectionHelp.getRandomWebElementFromCollection(webElementsList);
		branch.click();
		return branch;
	}


	/**
	 * Method select random section (if exists) and open it
	 */
	public static void clickOnRandomSection() {
		List<WebElement> webElementsList = sectionPage.getSectionList();
		assertionNotEmptyCollection(webElementsList);
		CollectionHelp.getRandomWebElementFromCollection(webElementsList).click();
	}

	/**
	 * Method select random topic (if exists) and open it
	 */
	public static void clickOnRandomTopic() {
		List<WebElement> webElementsList = topicPage.getTopicsList();
		assertionNotEmptyCollection(webElementsList);
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


	/**
	 * Method remove and create driver. Need to clean sessions variable
	 */
	public static void recreateDriver(boolean exist) {
		if (exist) {
			driver.close();
		}


		try {
			driver = new RemoteWebDriver(
					new URL(selServerURL),
					SeleniumConfig.getBrowserDriver(selDriverType));
			driver = new FirefoxDriver();

			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			createPages();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method create second driver
	 */
	public static void createSecondDriver() {
		try {
			driver2 = new RemoteWebDriver(
					new URL(selServerURL),
					SeleniumConfig.getBrowserDriver(selDriverType));
//			driver2 = new FirefoxDriver();
			driver2.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


	/*
	*New user registration
	 */
	public static void registerNewUser(String username, String email, String pass) {
		signUpPage.getSignUpButton().click();
		signUpPage.getUsernameField().sendKeys(username);
		signUpPage.getEmailField().sendKeys(email);
		signUpPage.getPasswordField().sendKeys(pass);
		signUpPage.getPasswordConfirmField().sendKeys(pass);
		signUpPage.getSubmitButton().click();
		//ok button on information window
		signUpPage.getOkButtonOnInfoWindow().click();
	}

	/**
	 * Method creates page for work methods of this class
	 */
	public static void createPages() {
		mainPage = new MainPage(driver);
		signInPage = new SignInPage(driver);
		signUpPage = new SignUpPage(driver);
		topicPage = new TopicPage(driver);
		postPage = new PostPage(driver);
		sectionPage = new SectionPage(driver);
		branchPage = new BranchPage(driver);
		logOutPage = new LogOutPage(driver);
		pmPage = new PMPage(driver);
		profilePage = new ProfilePage(driver);
	}

}
