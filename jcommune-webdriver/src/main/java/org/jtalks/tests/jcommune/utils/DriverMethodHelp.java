package org.jtalks.tests.jcommune.utils;

import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.getCapabilities;

/**
 * Utils class. Contains methods to find method by driver
 *
 * @author maxim reshetov
 */
public class DriverMethodHelp {

    public static String getTextFromTag(WebElement element) {
        switch (getCapabilities().getBrowserName().toLowerCase()) {
            case "firefox":
            case "ipad":
            case "chrome":
                return element.getAttribute("innerHTML");
            //htmlunit
            default:
                return element.getText();
        }
    }

    /**
     * Since HtmlUnit is kind of special case, it might be useful from time to time to check on that matter.
     *
     * @return true if currently HtmlUnit is used instead of real browser, false otherwise
     */
    public static boolean isDriverHtmlUnit() {
        return getCapabilities().getBrowserName().equalsIgnoreCase("htmlunit");
    }

    /**
     * Sets state for checkbox element
     *
     * @param checkboxElement the checkbox web element
     * @param state           the state: true - checked, false - unchecked, null - the element is not used
     */
    public static void setCheckboxState(WebElement checkboxElement, boolean state) {
        if (state && !checkboxElement.isSelected()) {
            checkboxElement.click();
        } else if (!state && checkboxElement.isSelected()) {
            checkboxElement.click();
        }
    }

    /**
     * Checks whether element is visible on the screen <i>right now</i>. Usual Selenium's
     * {@link org.openqa.selenium.WebElement#isDisplayed()} will wait for implicit time if the element is not there,
     * which is set to {@link org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig#SELENIUM_TIMEOUT_SEC}. But
     * at some places we might not want to wait that long, e.g. on the Topic Creation page the checkboxes like
     * <i>sticked</i> are not shown most of the time since user doesn't have enough permissions, so we don't want to
     * wait in most cases for a dozen of seconds.
     *
     * @param driver  webdriver that's being used to find the element to change its timeout to 0
     * @param element a web element we'd like to check for visibility
     * @return true if the element is visible, false if it's on the page and not visible or it's not on the page
     */
    public static boolean isElementDisplayedImmediately(WebDriver driver, WebElement element) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        }
    }

    /**
     * If alert is open, then closing it (pressing Ok) or doing nothing if there is no dialog open.
     *
     * @return true if dialog was actually closed, false otherwise
     * @throws RuntimeException well, web driver can throw anything it wants, we handle only
     *                          {@link org.openqa.selenium.NoAlertPresentException} and
     *                          {@link java.lang.UnsupportedOperationException} that can be thrown by HtmlUnit
     */
    public static boolean closeAlertIfExists(WebDriver driver) {
        try {
            driver.switchTo().alert().accept();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        } catch (WebDriverException e) {
            Throwable cause = e.getCause();
            if (cause == null) {
                info("Got exception when trying to close dialog, but it's not NoAlertException: " + e);
                throw e;
            }
            Class<? extends Throwable> causeClass = cause.getClass();
            if (causeClass != NoAlertPresentException.class && causeClass != UnsupportedOperationException.class) {
                info("Got exception when trying to close dialog: " + e);
                throw e;
            }
            //else nothing to do since there is no alert in the browser or browser doesn't support alerts
        }
        return false;
    }
}
