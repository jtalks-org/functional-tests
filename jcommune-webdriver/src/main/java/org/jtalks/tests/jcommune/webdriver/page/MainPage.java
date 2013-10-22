package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class MainPage {
    public static final String logOutButtonSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/logout']";
    public static final String breadCrumbsForumLinkSel = "//ul[@class='breadcrumb']//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/sections']";
    public static final String recentActivityLinkSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/topics/recent']";
    public static final String messagesCountSel = "//span[@class='test-messages']";
    public static final String usersCountSel = "//span[@class='test-registered-users']";
    public static final String usersOnlineCountSel = "//span[@class='test-visitors-total']";
    public static final String registeredUsersOnlineCountSel = "//span[@class='test-visitors-registered']";
    public static final String guestsUsersOnlineCountSel = "//span[@class='test-visitors-guests']";
    public static final String profileLinkSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user' and not(@class='currentusername')]";
    public static final String languageSwitcherSel = "//div[@id='lang-selector-toggle']/a/img";
    public static final String languageDropdownMenuSel = "//li[@class='dropdown open']";
    @FindBy(id = "mainLinksEditor")
    private WebElement modalDialog;
    @FindAll({@FindBy(id = "links_editor"), @FindBy(id = "links_editor_top")})
    private WebElement editExternalLinksControl;
    @FindBy(id = "user-dropdown-administration-link")
    private WebElement administrationDropdownMenu;
    @FindBy(id = "Administration")
    private WebElement toggleAdmineModeLink;
    @FindBy(xpath = languageDropdownMenuSel)
    private WebElement languageDropdownMenu;
    @FindBy(xpath = languageSwitcherSel)
    private WebElement languageSwitcher;
    @FindBy(xpath = profileLinkSel)
    private WebElement profileLink;
    @FindBy(xpath = logOutButtonSel)
    private WebElement logOutButton;
    @FindBy(id = "user-dropdown-menu-link")
    private WebElement userMenuLink;
    @FindBy(id = "signin")
    private WebElement loginLink;
    @FindBy(id = "signup")
    private WebElement registrationLink;
    @FindBy(className = "text_errorpage")
    private WebElement errorPage;
    @FindBy(xpath = breadCrumbsForumLinkSel)
    private WebElement breadCrumbsForumLink;
    @FindBy(className = "brand")
    private WebElement iconLinkToMainPage;
    @FindBy(xpath = recentActivityLinkSel)
    private WebElement recentActivityLink;
    @FindBy(xpath = messagesCountSel)
    private WebElement messagesCount;
    @FindBy(xpath = usersCountSel)
    private WebElement usersCount;
    @FindBy(xpath = usersOnlineCountSel)
    private WebElement usersOnlineCount;
    @FindBy(xpath = registeredUsersOnlineCountSel)
    private WebElement registeredUsersOnlineCount;
    @FindBy(xpath = guestsUsersOnlineCountSel)
    private WebElement guestsUsersOnlineCount;
    @FindBy(xpath = "//a[@class='brand']")
    private WebElement forumsTitle;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isAdminModeOn() {
        try {
            return editExternalLinksControl.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void pressOpenExternalLinksDialog() {
        editExternalLinksControl.click();
    }

    public void logOutIfLoggedIn(WebDriver driver) {
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            clickLogout();
        } catch (NoSuchElementException e) {
            //we don't care if user already is logged out
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public void clickRegistration() {
        registrationLink.click();
    }

    public void clickLogin() {
        loginLink.click();
    }

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

    public void clickForumsTitle() {
        forumsTitle.click();
    }

    public WebElement getIconLinkToMainPage() {
        return iconLinkToMainPage;
    }

    public WebElement getToggleAdmineModeLink() {
        return toggleAdmineModeLink;
    }

    public WebElement getAdministrationDropdownMenu() {
        return administrationDropdownMenu;
    }

    public WebElement getModalDialog() {
        return modalDialog;
    }
}
