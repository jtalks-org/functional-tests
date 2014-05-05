package org.jtalks.tests.jcommune.webdriver.page.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;


import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

public class SmallScreenHeader extends Header {
    @FindBy(id = "links_editor_top")
    protected WebElement editExternalLinksControl;

    public SmallScreenHeader(WebDriver driver) {
        super(driver);
    }

    public void clickLogin() {
        info("Clicking Login");
        openMenu();
        loginLink.click();
    }

    public void clickLogout() {
        openMenu();
        logOutButton.click();
    }

    @Override
    public void openPrivateMessages() {
        openMenu();
        privateMessagesLink.click();
    }

    @Override
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

    @Override
    public boolean isAdminModeOn() {
        openMenu();
        try {
            return editExternalLinksControl.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public void pressOpenExternalLinksDialog() {
        openMenu();
        info("Clicking a button to open External Links Editor");
        editExternalLinksControl.click();
    }

    public void pressOpenForumSettingsDialog() {
        info("Clicking a button to open Forum Settings Dialog");
        editForumSettingsControl.click();
    }

    private void openMenu() {
        info("Opening top menu on small screen...");
        if (isMenuHidden()) {
            info("Clicking on the menu button to open it");
            smallScreenMenuButton.click();
        }
    }

    private boolean isMenuHidden() {
        info("Checking whether search input is shown. If not - the menu is already opened.");
        //using search input because menu button itself doesn't have correct class 'collapsed' on the initial page load
        return !searchInput.isDisplayed();
    }
}
