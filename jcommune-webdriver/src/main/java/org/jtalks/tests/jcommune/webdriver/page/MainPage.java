package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author erik
 */
public class MainPage {
    public static final String logOutButtonSel = "//a[@href='" + JCommuneSeleniumTest.JCOMMUNE_CONTEXT_PATH + "/logout']";
    public static final String errorPageSel = "//div[@class='text_errorpage']";
    public static final String breadCrumbsForumLinkSel = "//ul[@class='breadcrumb']//a[@href='" + JCommuneSeleniumTest.JCOMMUNE_CONTEXT_PATH + "/sections']";
    public static final String iconLinkToMainPageSel = "//a[@href='" + JCommuneSeleniumTest.JCOMMUNE_CONTEXT_PATH + "/']";
    public static final String recentActivityLinkSel = "//a[@href='" + JCommuneSeleniumTest.JCOMMUNE_CONTEXT_PATH + "/topics/recent']";
    public static final String messagesCountSel = "//span[@class='test-messages']";
    public static final String usersCountSel = "//span[@class='test-registered-users']";
    public static final String usersOnlineCountSel = "//span[@class='test-visitors-total']";
    public static final String registeredUsersOnlineCountSel = "//span[@class='test-visitors-registered']";
    public static final String guestsUsersOnlineCountSel = "//span[@class='test-visitors-guests']";
    public static final String profileLinkSel = "//a[@href='" + JCommuneSeleniumTest.JCOMMUNE_CONTEXT_PATH + "/user' and not(@class='currentusername')]";
    public static final String languageSwitcherSel = "//div[@id='lang-selector-toggle']/a/img";
    public static final String languageDropdownMenuSel = "//li[@class='dropdown open']";

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

    public void logOutIfLoggedIn() {
        try {
            clickLogout();
        } catch (NoSuchElementException e) {
            //we don't care if user already is logged out
        }
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
}
