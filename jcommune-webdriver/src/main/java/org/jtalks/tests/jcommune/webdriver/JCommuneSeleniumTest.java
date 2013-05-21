package org.jtalks.tests.jcommune.webdriver;

import org.jtalks.tests.jcommune.webdriver.page.Pages;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JCommuneSeleniumTest {
    public static WebDriver driver = null;
    public static final String JCOMMUNE_CONTEXT_PATH = "/jcommune";
    private static final int SELENIUM_TIMEOUT = 10;
    public static String webdriverType;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server. All values
     * are stored in testng.xml file.
     *
     * @param webDriverUrl  selenium server URL, will be used by default if no SELENIUM_URL env var is set (like in
     *                      case with SauceLabs integration on Jenkins)
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"webDriverUrl", "appUrl"})
    public void init(String webDriverUrl, String appUrl) throws Exception {
        webdriverType = getBrowser();
        initDriver(webDriverUrl);
        Pages.createAllPages(driver);
    }

    private void initDriver(String defaultSeleniumServerUrl) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, getOs());
        capabilities.setBrowserName(getBrowser());
        capabilities.setVersion(getBrowserVersion());

        String seleniumUrl = getSeleniumUrl(defaultSeleniumServerUrl);
        logger.info("Selenium URL: [{}]", seleniumUrl);
        for (Map.Entry<String, String> var : System.getenv().entrySet()) {
            logger.info("{}={}", var.getKey(), var.getValue());
        }
        driver = new RemoteWebDriver(new URL(seleniumUrl), capabilities);
        driver.manage().timeouts().implicitlyWait(SELENIUM_TIMEOUT, TimeUnit.SECONDS);
    }

    private String getOs() {
        String os = System.getenv("SELENIUM_PLATFORM") == null ? "" : System.getenv("SELENIUM_PLATFORM");
        if (os.isEmpty()) {
            return Platform.ANY.toString();
        }
        return os;
    }

    private String getBrowserVersion() {
        return System.getenv("SELENIUM_VERSION") == null ? "" : System.getenv("SELENIUM_VERSION");
    }

    private String getBrowser() {
        return System.getenv("SELENIUM_BROWSER") == null ? "htmlunit" : System.getenv("SELENIUM_BROWSER");
    }

    private String getSeleniumUrl(String defaultUrl) {
        String url;
        String sauceApiKey = System.getenv("SAUCE_API_KEY");
        String sauceUsername = System.getenv("SAUCE_USER_NAME");
        String sauceHost = System.getenv("SELENIUM_HOST");
        if (sauceApiKey != null && sauceUsername != null) {
            url = "http://" + sauceUsername + ":" + sauceApiKey + "@" + sauceHost + ":80/wd/hub";
        } else {
            url = defaultUrl;
        }
        return url;
    }

    /** Method destroy connect with Selenium Server */
    @AfterSuite(alwaysRun = true)
    public void destroy() {
        driver.quit();
    }
}
