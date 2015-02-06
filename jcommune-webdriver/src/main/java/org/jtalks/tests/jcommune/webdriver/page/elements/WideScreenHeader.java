package org.jtalks.tests.jcommune.webdriver.page.elements;

import org.jtalks.tests.jcommune.assertion.Existence;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeoutException;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;

public class WideScreenHeader extends Header {

    public WideScreenHeader(WebDriver driver) {
        super(driver);
    }

    @Step
    public void clickLogin() {
        loginLink.click();
    }

    public void clickLogout() {
        openMainMenu();
        logOutButton.click();
    }

    public void openPrivateMessages() {
        openMainMenu();
        privateMessagesLink.click();
    }

    public boolean userIsLoggedIn() {
        try {
            return userMenuLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAdminModeOn() {
        info("Checking admin mode status");
        return Existence.existsImmediately(driver, editExternalLinksControl);
    }

    @Step
    public void pressOpenExternalLinksDialog() {
        info("Clicking a button to open External Links Editor");
        editExternalLinksControl.click();
    }

    @Step
    public void pressOpenForumSettingsDialog() {
        info("Clicking a button to open Forum Settings Dialog");
        editForumSettingsControl.click();
    }

    @Step
    public void switchingAdminMode() {
        info("Opening Administration context menu on top of the page");
        openAdminMenu();
        info("Choosing Enter/Exit Admin Mode menu item");
        toggleAdminModeLink.click();
    }

    private void openMainMenu() {
        userMenuLink.click();
        info("Clicked main menu");
        if (!mainPage.isDropdownMenuOpened()) {
            userMenuLink.click();
            info("Clicked main menu");
            if (!mainPage.isDropdownMenuOpened()) {
                throw new NoSuchElementException("Tried to open main menu 2 times without any success");
            }
        }
    }

    private void openAdminMenu() {
        administrationDropdownMenu.click();
        info("Clicked administration menu");
        if (!mainPage.isDropdownMenuOpened()) {
            administrationDropdownMenu.click();
            info("Clicked administration menu again");
            if (!mainPage.isDropdownMenuOpened()) {
                throw new NoSuchElementException("Tried to open administration menu 2 times without any success");
            }
        }
    }
}
