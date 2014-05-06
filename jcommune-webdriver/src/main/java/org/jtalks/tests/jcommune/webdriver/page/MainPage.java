package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.page.elements.Header;
import org.jtalks.tests.jcommune.webdriver.page.elements.SmallScreenHeader;
import org.jtalks.tests.jcommune.webdriver.page.elements.WideScreenHeader;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.getCapabilities;

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
    @FindBy(className = "btn-navbar")
    protected WebElement smallScreenMenuButton;
    @FindBy(id = "mainLinksEditor")
    private WebElement modalDialog;
    @FindAll({@FindBy(id = "links_editor"), @FindBy(id = "links_editor_top")})
    private WebElement editExternalLinksControl;
    @FindBy(id = "administration-modal-dialog")
    private WebElement forumSettingsDialog;
    @FindAll({@FindBy(id = "cmpName"), @FindBy(id = "cmpDescription")})
    private WebElement editForumSettingsControl;
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
    @FindBy(css = ".margin-left-small.test-pm-count")
    private WebElement mailCounter;
    @FindBy(xpath = "//a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/users')]")
    private WebElement lastPostAuthor;
    private volatile Header wideHeader;
    private volatile Header smallHeader;
    private WebDriver driver;


    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isAdminModeOn() {
        return getHeader().isAdminModeOn();
    }

    public void pressOpenExternalLinksDialog() {
        getHeader().pressOpenExternalLinksDialog();
    }

    public void pressOpenForumSettingsDialog() {
        getHeader().pressOpenForumSettingsDialog();
    }

    public void logOutIfLoggedIn(WebDriver driver) {
        info("Logging Out If Logged In");
        try {
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            clickLogout();
            info("User was successfully logged out");
        } catch (NoSuchElementException e) {
            info("User was not logged in, so no one to log out");
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public void clickRegistration() {
        registrationLink.click();
    }

    public void clickLogin() {
        info("Clicking login");
        getHeader().clickLogin();
    }

    public void clickLogout() {
        info("Clicking logout");
        getHeader().clickLogout();
    }

    public void openPrivateMessages() {
        info("Clicking private messages");
        getHeader().openPrivateMessages();
    }

    public boolean userIsLoggedIn() {
        return getHeader().userIsLoggedIn();
    }

    public void clickForumsTitle() {
        forumsTitle.click();
    }

    public WebElement getIconLinkToMainPage() {
        return iconLinkToMainPage;
    }

    public void switchOnAdminMode() {
        if (!isAdminModeOn()) {
            info("Opening Administration context menu on top of the page");
            administrationDropdownMenu.click();
            info("Choosing Enter Admin Mode menu item");
            toggleAdmineModeLink.click();
        }
    }

    public void switchOffAdminMode() {
        if (isAdminModeOn()) {
            info("Opening Administration context menu on top of the page");
            administrationDropdownMenu.click();
            info("Choosing Exit Admin Mode menu item");
            toggleAdmineModeLink.click();
        }
    }

    public WebElement getModalDialog() {
        return modalDialog;
    }

    public WebElement getForumSettingsDialog() {
        return forumSettingsDialog;
    }

    public WebElement getMailCounter() {
        return mailCounter;
    }

    private Header getSmallHeader() {
        if (smallHeader == null) {
            smallHeader = new SmallScreenHeader(driver);
        }
        return smallHeader;
    }

    private Header getWideHeader() {
        if (wideHeader == null) {
            wideHeader = new WideScreenHeader(driver);
        }
        return wideHeader;
    }

    public Header getHeader() {
        if (isInSmallScreenMode()) {
            return getSmallHeader();
        } else {
            return getWideHeader();
        }
    }

    public boolean isInSmallScreenMode() {
        info("Checking whether we're in small screen mode");
        try {
            boolean displayed = false;
            //htmlunit thinks we're in small screen mode while we aren't
            if (!"htmlunit".equalsIgnoreCase(getCapabilities().getBrowserName())) {
                displayed = smallScreenMenuButton.isDisplayed();
            }
            if (displayed) {
                info("We're in a small screen mode");
            } else {
                info("We're in a wide screen mode");
            }
            return displayed;
        } catch (NoSuchElementException e) {
            info("We're in a wide screen mode");
            return false;
        }
    }

    public WebElement getLastPostAuthor() {
        return lastPostAuthor;
    }
}
