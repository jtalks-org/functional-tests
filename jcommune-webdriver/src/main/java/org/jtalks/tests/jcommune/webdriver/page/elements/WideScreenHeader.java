package org.jtalks.tests.jcommune.webdriver.page.elements;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

public class WideScreenHeader extends Header {
    public WideScreenHeader(WebDriver driver) {
        super(driver);
    }

    @Override
    public void clickLogin() {
        loginLink.click();
    }

    @Override
    public void clickLogout() {
        userMenuLink.click();
        logOutButton.click();
    }

    public boolean userIsLoggedIn() {
        try {
            return userMenuLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAdminModeOn() {
        try {
            return editExternalLinksControl.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void pressOpenExternalLinksDialog() {
        info("Clicking a button to open External Links Editor");
        editExternalLinksControl.click();
    }
}
