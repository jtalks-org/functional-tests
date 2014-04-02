package org.jtalks.tests.jcommune.webdriver;

import org.jtalks.tests.jcommune.webdriver.page.Pages;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class JCommuneSeleniumConfig {
    public static final String JCOMMUNE_CONTEXT_PATH = "/jcommune";
    private final static Logger LOGGER = LoggerFactory.getLogger(JCommuneSeleniumConfig.class);
    /**
     * Amount of time we wait for an element to appear if it's not on the screen yet.
     */
    public static final int SELENIUM_TIMEOUT_SEC = 10;
    public static WebDriver driver = null;
    public static String webdriverType;
    private static String appUrl;

    public static Capabilities getCapabilities() {
        return ((RemoteWebDriver) driver).getCapabilities();
    }

    /**
     * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server. All values
     * are stored in testng.xml file.
     *
     * @param webDriverUrl selenium server URL, will be used by default if no SELENIUM_URL env var is set (like in case
     *                     with SauceLabs integration on Jenkins)
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"webDriverUrl"})
    public void init(String webDriverUrl, String appUrl) throws Exception {
        JCommuneSeleniumConfig.appUrl = appUrl;
        webdriverType = getBrowser();
        initDriver(webDriverUrl);
        Pages.createAllPages(driver);
    }

    private void initDriver(String defaultSeleniumServerUrl) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PLATFORM, getOs());
        capabilities.setBrowserName(getBrowser());
        capabilities.setVersion(getBrowserVersion());
        capabilities.setCapability("max-duration", 10800);//max duration of a single test class in seconds (10800=3hrs)

        String seleniumUrl = getSeleniumUrl(defaultSeleniumServerUrl);
        LOGGER.info("{}", capabilities);
        LOGGER.info("Selenium WebDriver URL: [{}]", seleniumUrl);
        driver = new RemoteWebDriver(new URL(seleniumUrl), capabilities);
        driver.manage().timeouts().implicitlyWait(SELENIUM_TIMEOUT_SEC, TimeUnit.SECONDS);
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
        String sauceDriver = System.getenv("SELENIUM_DRIVER");
        String seleniumUrl = System.getenv("SELENIUM_URL");
        if (isNotEmpty(sauceDriver)) {
            String sauceUsername = sauceDriver.substring(sauceDriver.indexOf("username"),
                    sauceDriver.lastIndexOf("&")).split("=")[1];
            String sauceApiKey = sauceDriver.substring(sauceDriver.indexOf("access-key"),
                    sauceDriver.length()).split("=")[1];
            String sauceHost = "@ondemand.saucelabs.com:80/wd/hub";
            url = "http://" + sauceUsername + ":" + sauceApiKey + sauceHost;
        } else if (isNotEmpty(seleniumUrl)) {
            url = seleniumUrl;
        } else {
            url = defaultUrl;
        }
        return url;
    }

    public void printSeleniumSessionId(String currentTestClass) {
        String sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", sessionId, currentTestClass);
        System.out.println(message);
    }

    /**
     * Method destroy connect with Selenium Server
     */
    public void destroy() {
        if (!driver.toString().contains("null")) { //if null, then driver already quit
            driver.quit();
        }
    }

    public static String getAppUrl() {
        return appUrl;
    }
}
