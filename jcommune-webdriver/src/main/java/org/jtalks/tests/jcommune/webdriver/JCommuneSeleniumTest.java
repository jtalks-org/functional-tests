package org.jtalks.tests.jcommune.webdriver;

import org.jtalks.tests.jcommune.assertion.Existance;
import org.jtalks.tests.jcommune.mail.Mail;
import org.jtalks.tests.jcommune.mail.MailHelp;
import org.jtalks.tests.jcommune.utils.CollectionHelp;
import org.jtalks.tests.jcommune.utils.SeleniumConfig;
import org.jtalks.tests.jcommune.utils.StringHelp;
import org.jtalks.tests.jcommune.webdriver.page.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public static final String contextPath = "/jcommune";

    public static String selServerURL;

    public static String selDriverType;

    /*
          * instance to test configured it.
           */
    public static String validCaptchaValue = "0000";


    //Pages for common methods
    public static MainPage mainPage;

    public static SignInPage signInPage;

    public static SignUpPage signUpPage;

    public static TopicPage topicPage;

    public static PostPage postPage;

    public static SectionPage sectionPage;

    public static BranchPage branchPage;
    public static PMPage pmPage;

    public static ProfilePage profilePage;

    // Constants to logger
    public static final String beforeTestLog = "Setup test";

    public static final String runTestLog = "Run test";

    public static final String afterTestLog = "Destroy test";

    public static final String elNotFoundLog = "Element not found";

    public static final String elementsNotFoundLog = "Elements not found";

    //Mail implementation
    public static Mail mailServer;

    public static int timeout = 10;

    /**
     * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server.
     * All values are stored in tesng.xml file.
     *
     * @param selServerURL     Selenium server URL
     * @param selDriverType    Selenium driver type
     * @param mail             Mail server tpe
     * @param publicEmailLogin Login to sign in in publick email, which contains all mails sended to *jtalks.org
     * @param publicEmailPass  Pass to publicEmail
     * @throws MalformedURLException
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"selenium-server-url", "selenium-driver-type", "app-url", "uUsername", "uPassword", "uEmail", "uUsername2", "uPassword2", "uEmail2",
            "uUsername3", "uPassword3", "uEmail3", "uUsername4", "uPassword4", "uEmail4", "aUsername", "aPassword", "aEmail", "mail", "publicemail", "publicpass"})
    public void init(String selServerURL, String selDriverType, String appUrl, String uUsername, String uPassword, String uEmail, String uUsername2,
                     String uPassword2,
                     String uEmail2, String uUsername3, String uPassword3, String uEmail3, String uUsername4, String uPassword4, String uEmail4,
                     String aUsername, String aPassword, String aEmail, String mail, String publicEmailLogin, String publicEmailPass) throws Exception {

        this.selDriverType = selDriverType;

        this.selServerURL = selServerURL;

        recreateDriver(false);
        createSecondDriver();

        mailServer = MailHelp.getMailImpl(mail);

        HashMap<Object, String[]> users = new HashMap();
        users.put(1, new String[]{uUsername, uEmail, uPassword});
        users.put(2, new String[]{uUsername2, uEmail2, uPassword2});
        users.put(3, new String[]{uUsername3, uEmail3, uPassword3});
        users.put(4, new String[]{uUsername4, uEmail4, uPassword4});
        users.put(5, new String[]{aUsername, aEmail, aPassword});


        createPages();

//		usersCheck(users, appUrl, publicEmailLogin, publicEmailPass);
    }

    /**
     * Method destroy connect with Selenium Server
     */
    @AfterSuite(alwaysRun = true)
    public void destroy() {
        driver.quit();
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {

            }
        }
    }


    public static boolean signIn(String username, String password) {
        mainPage.clickLogin();
        signInPage.getUsernameField().sendKeys(username);
        signInPage.getPasswordField().sendKeys(password);
        signInPage.getSubmitButton().click();
        return mainPage.userIsLoggedIn();
    }


    /**
     * Method  used static link because in selenium exist bag  MoveTargetOutOfBoundsError
     */
    public static void logOutIfLoggedIn() {
        try {
            mainPage.clickLogout();
        } catch (NoSuchElementException e) {
            //we don't care if user already is logged out
        }
    }

    /**
     * Method  select random branch (if exists) and open it
     *
     * @return Selected branch
     */
    public static WebElement clickOnRandomBranch() {
        List<WebElement> webElementsList = branchPage.getBranchList();
        Existance.assertionNotEmptyCollection(webElementsList);
        WebElement branch = CollectionHelp.getRandomWebElementFromCollection(webElementsList);
        branch.click();
        return branch;
    }

    /**
     * Method  select random branch (if exists) from section page and open it
     *
     * @return Selected branch
     */
    public static WebElement clickOnRandomBranchFromSectionPage() {
        List<WebElement> webElementsList = branchPage.getBranchList();
        Existance.assertionNotEmptyCollection(webElementsList);
        WebElement branch = CollectionHelp.getRandomWebElementFromCollection(webElementsList);
        branch.click();
        return branch;
    }


    /**
     * Method select random section (if exists) and open it
     */
    public static void clickOnRandomSection() {
        List<WebElement> webElementsList = sectionPage.getSectionList();
        Existance.assertionNotEmptyCollection(webElementsList);
        CollectionHelp.getRandomWebElementFromCollection(webElementsList).click();
    }

    /**
     * Method select random topic (if exists) and open it
     */
    public static void clickOnRandomTopic() {
        List<WebElement> webElementsList = topicPage.getTopicsList();
        Existance.assertionNotEmptyCollection(webElementsList);
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
//            driver = new FirefoxDriver();

            driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

            createPages();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
            driver2.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

        } catch (MalformedURLException e) {
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
        signUpPage.getCaptchaField().sendKeys(validCaptchaValue);
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
        pmPage = new PMPage(driver);
        profilePage = new ProfilePage(driver);
    }

    /**
     * method creates posts for tests.
     * you should be in topic to launch this method
     *
     * @param countOfPosts
     * @param lengthOfPost
     */
    public static void createPostsForTest(int countOfPosts, int lengthOfPost) {
        for (int i = 0; i < countOfPosts; i++) {
            postPage.getNewButton().click();
            postPage.getMessageField().sendKeys(StringHelp.getRandomString(lengthOfPost));
            postPage.getPostButton().click();
        }
    }


}
