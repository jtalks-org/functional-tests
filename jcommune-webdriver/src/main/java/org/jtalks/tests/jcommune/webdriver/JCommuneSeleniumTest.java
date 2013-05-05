package org.jtalks.tests.jcommune.webdriver;

import org.jtalks.tests.jcommune.utils.SeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.page.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class JCommuneSeleniumTest {
    /**
     * Object to work with Remote Selenium Server
     */
    public static WebDriver driver = null;
    //Second driver, if need two browsers
    public static WebDriver driver2 = null;
    public static final String contextPath = "/jcommune";
    public static String selServerURL;
    public static String selDriverType;
    /**
     * JCommune instance for functional should be configured to use only 0000 as captcha so that we can actually
     * register users without writing a tool to break our own captcha
     */
    public static final String VALID_CAPTCHA_VALUE = "0000";

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
    public static int timeout = 10;

    /**
     * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server.
     * All values are stored in tesng.xml file.
     *
     * @param selServerURL     Selenium server URL
     * @param selDriverType    Selenium driver type
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"selenium-server-url", "selenium-driver-type", "app-url"})
    public void init(String selServerURL, String selDriverType, String appUrl) throws Exception {
        JCommuneSeleniumTest.selDriverType = selDriverType;
        JCommuneSeleniumTest.selServerURL = selServerURL;
        recreateDriver();
        createSecondDriver();
        createPages();
    }

    /**
     * Method destroy connect with Selenium Server
     */
    @AfterSuite(alwaysRun = true)
    public void destroy() {
        driver.quit();
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
     * Method remove and create driver. Need to clean sessions variable
     */
    public static void recreateDriver() {
        try {
            driver = new RemoteWebDriver(new URL(selServerURL), SeleniumConfig.getBrowserDriver(selDriverType));
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
            driver2.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
}
