package org.jtalks.tests.jcommune.webdriver;

import org.jtalks.tests.jcommune.webdriver.page.Pages;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class JCommuneSeleniumTest {
    /** Object to work with Remote Selenium Server */
    public static WebDriver driver = null;
    public static final String JCOMMUNE_CONTEXT_PATH = "/jcommune";
    private static final int SELENIUM_TIMEOUT = 10;
    public static String webdriverType;

    /**
     * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server. All values
     * are stored in testng.xml file.
     *
     * @param selServerURL  Selenium server URL
     * @param browser Selenium driver type
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"seleniumServerUrl", "appUrl", "browser", "browserVersion", "os"})
    public void init(String selServerURL, String appUrl, String browser, String browserVersion,
                     String os) throws Exception {
        webdriverType = browser;
        initDriver(selServerURL, browser, browserVersion, os);
        Pages.createAllPages(driver);
    }

    private void initDriver(String seleniumServerUrl, String browser,
                            String browserVersion, String os) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities(getBrowser(browser),
                getBrowserVersion(browserVersion), getOs(os));
        driver = new RemoteWebDriver(new URL(seleniumServerUrl), capabilities);
        driver.manage().timeouts().implicitlyWait(SELENIUM_TIMEOUT, TimeUnit.SECONDS);
    }

    private Platform getOs(String defaultOs) {
        String os = System.getenv("SELENIUM_PLATFORM") == null ? defaultOs : System.getenv("SELENIUM_PLATFORM");
        return Platform.valueOf(os);
    }

    private String getBrowserVersion(String defaultVersion) {
        return System.getenv("SELENIUM_VERSION") == null ? defaultVersion : System.getenv("SELENIUM_VERSION");
    }

    private String getBrowser(String defaultBrowser) {
        return System.getenv("SELENIUM_BROWSER") == null ? defaultBrowser : System.getenv("SELENIUM_BROWSER");
    }

    /** Method destroy connect with Selenium Server */
    @AfterSuite(alwaysRun = true)
    public void destroy() {
        driver.quit();
    }
}
