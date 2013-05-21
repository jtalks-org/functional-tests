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
    public static WebDriver driver = null;
    public static final String JCOMMUNE_CONTEXT_PATH = "/jcommune";
    private static final int SELENIUM_TIMEOUT = 10;
    public static String webdriverType;

    /**
     * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server. All values
     * are stored in testng.xml file.
     *
     * @param webDriverUrl  Selenium server URL
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"webDriverUrl", "appUrl"})
    public void init(String webDriverUrl, String appUrl) throws Exception {
        webdriverType = getBrowser();
        initDriver(webDriverUrl);
        Pages.createAllPages(driver);
    }

    private void initDriver(String seleniumServerUrl) throws
            MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities(getBrowser(),
                getBrowserVersion(), getOs());
        driver = new RemoteWebDriver(new URL(seleniumServerUrl), capabilities);
        driver.manage().timeouts().implicitlyWait(SELENIUM_TIMEOUT, TimeUnit.SECONDS);
    }

    private Platform getOs() {
        String os = System.getenv("SELENIUM_PLATFORM") == null ? "" : System.getenv("SELENIUM_PLATFORM");
        if (os.isEmpty()) {
            return Platform.ANY;
        }
        return Platform.valueOf(os);
    }

    private String getBrowserVersion() {
        return System.getenv("SELENIUM_VERSION") == null ? "" : System.getenv("SELENIUM_VERSION");
    }

    private String getBrowser() {
        return System.getenv("SELENIUM_BROWSER") == null ? "htmlunit" : System.getenv("SELENIUM_BROWSER");
    }

    /** Method destroy connect with Selenium Server */
    @AfterSuite(alwaysRun = true)
    public void destroy() {
        driver.quit();
    }
}
