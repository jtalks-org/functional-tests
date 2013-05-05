package org.jtalks.tests.jcommune.webdriver;

import org.jtalks.tests.jcommune.utils.SeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.page.Pages;
import org.openqa.selenium.WebDriver;
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
     * @param selDriverType Selenium driver type
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"selenium-server-url", "selenium-driver-type", "app-url"})
    public void init(String selServerURL, String selDriverType, String appUrl) throws Exception {
        webdriverType = selDriverType;
        initDriver(selServerURL, selDriverType);
        Pages.createAllPages(driver);
    }

    private void initDriver(String seleniumServerUrl, String webdriverType) throws MalformedURLException {
        driver = new RemoteWebDriver(new URL(seleniumServerUrl), SeleniumConfig.getBrowserDriver(webdriverType));
        driver.manage().timeouts().implicitlyWait(SELENIUM_TIMEOUT, TimeUnit.SECONDS);
    }

    /** Method destroy connect with Selenium Server */
    @AfterSuite(alwaysRun = true)
    public void destroy() {
        driver.quit();
    }
}
