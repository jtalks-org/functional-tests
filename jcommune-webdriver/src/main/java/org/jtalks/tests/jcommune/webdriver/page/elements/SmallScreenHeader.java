package org.jtalks.tests.jcommune.webdriver.page.elements;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        try {
            return userMenuLink.isDisplayed();
        } catch (NoSuchElementException e) {
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

    private void openMenu() {
        if (isMenuHidden()) {
            smallScreenMenuButton.click();
        }
    }

    private boolean isMenuHidden() {
        //using search input because menu button itself doesn't have correct class 'collapsed' on the initial page load
        return !searchInput.isDisplayed();
    }
}
