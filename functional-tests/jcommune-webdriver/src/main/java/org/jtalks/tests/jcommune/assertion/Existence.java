package org.jtalks.tests.jcommune.assertion;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.ERROR_ELEMENT_SHORT_TIMEOUT_SEC;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.SELENIUM_TIMEOUT_SEC;

import java.util.concurrent.TimeUnit;

/**
 * The class contains a check for the existence of elements
 *
 * @author masyan
 */
public class Existence {

    /**
     * The method checks that the element does exist
     *
     * @param selector Selector to find element
     * @deprecated use {@link #assertElementExistsBySelector(org.openqa.selenium.WebDriver, org.openqa.selenium.By)}
     */
    @Deprecated
    public static void assertElementExistsBySelector(WebDriver driver, String selector) {
        try {
            driver.findElement(By.xpath(selector));
        } catch (NoSuchElementException e) {
            Assert.fail("The element with selector=\"" + selector + "\" should exist");
        }
    }

    /**
     * The method asserts that the element exists, otherwise throws exception.
     *
     * @param selector selector to find element
     */
    public static void assertElementExistsBySelector(WebDriver driver, By selector) {
        try {
            driver.findElement(selector);
        } catch (NoSuchElementException e) {
            Assert.fail("The element with selector=\"" + selector + "\" should exist");
        }
    }

    public static boolean exists(WebDriver driver, By selector) {
        try {
            Existence.assertElementExistsBySelector(driver, selector);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static boolean exists(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }

    public static boolean existsUsingLowerTimeout(WebDriver driver, WebElement element) {
        try {
            driver.manage().timeouts().implicitlyWait(ERROR_ELEMENT_SHORT_TIMEOUT_SEC, TimeUnit.SECONDS);
            element.isDisplayed();
            return true;
        } catch (NoSuchElementException ignored) {
            return false;
        } finally {
            driver.manage().timeouts().implicitlyWait(SELENIUM_TIMEOUT_SEC, TimeUnit.SECONDS);
        }
    }

    public static boolean existsImmediately(WebDriver driver, WebElement element) {
        return DriverMethodHelp.isElementDisplayedImmediately(driver, element);
    }
}
