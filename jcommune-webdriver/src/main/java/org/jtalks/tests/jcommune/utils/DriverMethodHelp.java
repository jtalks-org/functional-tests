package org.jtalks.tests.jcommune.utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

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
}
