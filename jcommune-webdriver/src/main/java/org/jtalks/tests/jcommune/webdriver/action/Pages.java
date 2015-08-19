package org.jtalks.tests.jcommune.webdriver.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import static org.jtalks.tests.jcommune.utils.Utils.CLOSE_CURRENT_TAB_CHORD;
import static org.jtalks.tests.jcommune.utils.Utils.OPEN_NEW_TAB_CHORD;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.getJs;

/**
 * @author baranov.r.p
 */
public class Pages {

    @Step
    public static void refreshPage() {
        driver.navigate().refresh();
    }

    @Step
    public static void scrollToEl(WebElement el) {
        getJs().executeScript("arguments[0].scrollIntoView(false);", el);
    }

    @Step
    public static void openAndCloseNewTab() {
        By bodyTag = By.tagName("body");
        driver.findElement(bodyTag).sendKeys(OPEN_NEW_TAB_CHORD);
        driver.findElement(bodyTag).sendKeys(CLOSE_CURRENT_TAB_CHORD);
    }
}