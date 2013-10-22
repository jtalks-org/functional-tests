package org.jtalks.tests.jcommune.utils;

import org.openqa.selenium.WebElement;

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
}
