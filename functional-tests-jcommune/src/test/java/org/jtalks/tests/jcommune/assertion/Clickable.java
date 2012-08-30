package org.jtalks.tests.jcommune.assertion;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.thoughtworks.selenium.SeleneseTestBase.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * The class contains methods who check if elements are enabled or disabled
 *
 * @author yacov
 */
public class Clickable {
    /**
     * The method checks that the element is _disabled_
     *
     * @param selector is Element Xpath Selector
     */
    public static void assertDisabled(WebDriver driver, String selector) {
        WebElement button = driver.findElement(By.xpath(selector));
        assertFalse("The element with selector =\"" + selector + "\" is not Disabled", button.isEnabled());
    }

    /**
     * The method checks that the element is _enabled_
     *
     * @param selector is Element Xpath Selector
     */
    public static void assertEnabled(WebDriver driver, String selector) {
        WebElement button = driver.findElement(By.xpath(selector));
        assertTrue("The element with selector =\"" + selector + "\" is not Enabled", button.isEnabled());
    }
}
