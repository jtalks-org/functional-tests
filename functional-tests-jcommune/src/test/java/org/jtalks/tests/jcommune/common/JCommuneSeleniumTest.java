package org.jtalks.tests.jcommune.common;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.PMPage;
import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.jtalks.tests.jcommune.pages.SectionPage;
import org.jtalks.tests.jcommune.pages.SignInPage;
import org.jtalks.tests.jcommune.pages.SignUpPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
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
import utils.CollectionHelp;
import utils.SeleniumConfig;
import utils.StringHelp;
import utils.mail.Mail;
import utils.mail.MailHelp;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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
        driver.close();
        if (dbConnection != null) {
            try {
                dbConnection.close();
            } catch (SQLException e) {

            }
        }
    }

    /**
     * Method checks users in application. If user not exists, then registering it.
     */
    private void usersCheck(HashMap<Object, String[]> users, String appUrl, String publicEmail, String publicPass) {
        driver.get(appUrl);
        for (String[] user : users.values()) {
            boolean result = signIn(user[0], user[2]);
            if (result) {
                logOut(appUrl);
            } else {
                //registration user
                signInPage.getCloseSignInWindowButton().click();

                registerNewUser(user[0], user[1], user[2]);

                mailServer.goToMailServer();
                //user with name "random" used in recovery password test. He have real email
                if (user[0].equals("tochanges")) {
                    mailServer.signIn(user[1], user[0]);
                } else {
                    mailServer.signIn(publicEmail, publicPass);
                }

                mailServer.openFirstMessage();
                String activeLink = mailServer.getFirstLinkInMessageText().getText();

                driver.get(activeLink);
                signInPage.getUsernameField().sendKeys(user[0]);
                signInPage.getPasswordField().sendKeys(user[2]);
                signInPage.getSubmitButton().click();

                profilePage.getCurrentUserLink().click();
                profilePage.getEditProfileButton().click();
                Select selectBox = new Select(profilePage.getPageSizeField());
                selectBox.selectByValue("5");
                profilePage.getSaveEditButton().click();
                logOut(appUrl);
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
    public static boolean signIn(String username, String password) {
        mainPage.getLoginLink().click();
        signInPage.getUsernameField().sendKeys(username);
        signInPage.getPasswordField().sendKeys(password);
        signInPage.getSubmitButton().click();
        //wait until user is logining
        try {
            signInPage.getErrorMessage().isDisplayed();
            return false;
        } catch (NoSuchElementException ex) {
            return (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return mainPage.getCurrentUsernameLink().isDisplayed();
                }
            });
        }
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
            final MainPage mainPage = new MainPage(driver2);
            SignInPage signInPage = new SignInPage(driver2);
            mainPage.getLoginLink().click();
            signInPage.getUsernameField().sendKeys(username);
            signInPage.getPasswordField().sendKeys(password);
            signInPage.getSubmitButton().click();
            //wait until usern is logining
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return mainPage.getCurrentUsernameLink().isDisplayed();
                }
            });
        }
    }


    /**
     * This method checks that link for logout present, than click on it
     * Method  used static link because in selenium exist bag  MoveTargetOutOfBoundsError
     *
     * @author erik
     */
    public static void logOut(String appUrl) {
        mainPage.getLogOutButton().click();
    }

    /**
     * This method checks that link for logout present, than click on it
     * Method  used static link because in selenium exist bag  MoveTargetOutOfBoundsError
     *
     * @author masyan
     */
    public static void logOutByAnotherDriver() {
        if (driver2 != null) {
            mainPage.getLogOutButton().click();
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
     * Method  select random branch (if exists) from section page and open it
     *
     * @return Selected branch
     */
    public static WebElement clickOnRandomBranchFromSectionPage() {
        List<WebElement> webElementsList = branchPage.getBranchListFromSectionPage();
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
//			driver = new FirefoxDriver();

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            createPages();
        } catch (MalformedURLException e) {
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
