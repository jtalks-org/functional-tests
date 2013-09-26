package org.jtalks.tests.jcommune.assertion;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

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
     */
    public static void assertElementExistsBySelector(WebDriver driver, String selector) {
        try {
            driver.findElement(By.xpath(selector));
        } catch (NoSuchElementException e) {
            Assert.fail("The element with selector=\"" + selector + "\" should exist");
        }
    }

    public static boolean exists(WebDriver driver, String selector) {
        try {
            Existence.assertElementExistsBySelector(driver, selector);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

}
