package org.jtalks.tests.jcommune.webdriver.action;

import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

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
}