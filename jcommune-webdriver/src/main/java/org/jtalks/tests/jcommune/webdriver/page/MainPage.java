package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author erik
 */
public class MainPage {
    public static final String logOutButtonSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/logout']";
    public static final String errorPageSel = "//div[@class='text_errorpage']";
    public static final String breadCrumbsForumLinkSel = "//ul[@class='breadcrumb']//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/sections']";
    public static final String iconLinkToMainPageSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/']";
    public static final String recentActivityLinkSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/topics/recent']";
    public static final String messagesCountSel = "//span[@class='test-messages']";
    public static final String usersCountSel = "//span[@class='test-registered-users']";
    public static final String usersOnlineCountSel = "//span[@class='test-visitors-total']";
    public static final String registeredUsersOnlineCountSel = "//span[@class='test-visitors-registered']";
    public static final String guestsUsersOnlineCountSel = "//span[@class='test-visitors-guests']";
    public static final String profileLinkSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user' and not(@class='currentusername')]";
    public static final String languageSwitcherSel = "//div[@id='lang-selector-toggle']/a/img";
    public static final String languageDropdownMenuSel = "//li[@class='dropdown open']";
    public static final String onAdminModeSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/admin/enter']";
    public static final String offAdminModeSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/admin/exit']";

    @FindBy(className = "modal-body")
    private List<WebElement> modalDialog;

    @FindBy(id = "user-dropdown-administration-link")
    private WebElement administrationDropdownMenu;

    @FindBy(xpath = onAdminModeSel)
    private WebElement onAdminModeBut;

    @FindBy(xpath = offAdminModeSel)
    private WebElement offAdminModeBut;

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

    @FindBy(xpath = errorPageSel)
    private WebElement errorPage;

    @FindBy(xpath = breadCrumbsForumLinkSel)
    private WebElement breadCrumbsForumLink;

    @FindBy(xpath = iconLinkToMainPageSel)
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


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Getters
    public WebElement getLanguageDropdownMenu() {
        return languageDropdownMenu;
    }

    public WebElement getLanguageSwitcher() {
        return languageSwitcher;
    }

    public WebElement getProfileLink() {
        return profileLink;
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

    public WebElement getErrorPage() {
        return errorPage;
    }

    public WebElement getBreadCrumbsForumLink() {
        return breadCrumbsForumLink;
    }

    public WebElement getIconLinkToMainPage() {
        return iconLinkToMainPage;
    }

    public WebElement getRecentActivityLink() {
        return recentActivityLink;
    }

    public WebElement getMessagesCount() {
        return messagesCount;
    }

    public WebElement getUsersCount() {
        return usersCount;
    }

    public WebElement getUsersOnlineCount() {
        return usersOnlineCount;
    }

    public WebElement getRegisteredUsersOnlineCount() {
        return registeredUsersOnlineCount;
    }

    public WebElement getGuestsUsersOnlineCount() {
        return guestsUsersOnlineCount;
    }

    public WebElement getOnAdminModeBut() {
        return onAdminModeBut;
    }

    public void setOnAdminModeBut(WebElement onAdminModeBut) {
        this.onAdminModeBut = onAdminModeBut;
    }

    public WebElement getOffAdminModeBut() {
        return offAdminModeBut;
    }

    public void setOffAdminModeBut(WebElement offAdminModeBut) {
        this.offAdminModeBut = offAdminModeBut;
    }

    public WebElement getAdministrationDropdownMenu() {
        return administrationDropdownMenu;
    }

    public void setAdministrationDropdownMenu(WebElement administrationDropdownMenu) {
        this.administrationDropdownMenu = administrationDropdownMenu;
    }

    public List<WebElement> getModalDialog() {
        return modalDialog;
    }

    public void setModalDialog(List<WebElement> modalDialog) {
        this.modalDialog = modalDialog;
    }
}
