package org.jtalks.tests.jcommune.webdriver.page.elements;

import org.jtalks.tests.jcommune.assertion.Existence;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.allure.annotations.Step;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;


import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

public class SmallScreenHeader extends Header {
    @FindBy(id = "links_editor_top")
    protected WebElement editExternalLinksControl;

    public SmallScreenHeader(WebDriver driver) {
        super(driver);
    }

    @Step
    public void clickLogin() {
        openMenu();
        loginLink.click();
    }

    public void clickLogout() {
        openMenu();
        logOutButton.click();
    }

    public void openPrivateMessages() {
        openMenu();
        privateMessagesLink.click();
    }

    @Step
    public boolean userIsLoggedIn() {
        openMenu();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-navbar")));
        try {
            info("Check whether user menu link is shown.");
            return userMenuLink.isDisplayed();
        } catch (NoSuchElementException e) {
            info("User menu link is not shown, the user is not logged in");
            return false;
        }
    }

    public boolean isAdminModeOn() {
        openMenu();
        return Existence.existsImmediately(driver, editExternalLinksControl);
    }

    @Step
    public void pressOpenExternalLinksDialog() {
        openMenu();
        info("Clicking a button to open External Links Editor");
        editExternalLinksControl.click();
    }

    @Step
    public void pressOpenForumSettingsDialog() {
        info("Clicking a button to open Forum Settings Dialog");
        editForumSettingsControl.click();
    }

    @Step
    private void openMenu() {
        info("Opening top menu on small screen...");
        if (isMenuHidden()) {
            info("Clicking on the menu button to open it");
            smallScreenMenuButton.click();
        }
    }

    @Step
    private boolean isMenuHidden() {
        info("Checking whether search input is shown. If not - the menu is already opened.");
        //using search input because menu button itself doesn't have correct class 'collapsed' on the initial page load
        return !searchInput.isDisplayed();
    }

    @Step
    public void switchingAdminMode() {
        openMenu();
        info("Choosing Enter/Exit Admin Mode menu item");
        toggleAdminModeLink.click();
    }
}
