package org.jtalks.tests.jcommune.utils;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Utils class. Contains methods to find method by driver
 *
 * @author maxim reshetov
 */
public class DriverMethodHelp {

  public static String getTextFromTag(WebElement element){
    switch(getCapabilities().getBrowserName()){
        case "firefox":
        case "chrome":
          return element.getAttribute("innerHTML");
        //htmlunit
        default :
          return element.getText();
    }
  }
}
