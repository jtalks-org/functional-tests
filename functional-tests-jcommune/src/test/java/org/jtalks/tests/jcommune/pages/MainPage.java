package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: erik
 * Date: 03.03.12
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
public class MainPage {

    private static final String loginLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/login']";

    public static final String currentUsernameLinkSel = "//a[@class='currentusername']";

    public static final String errorPageSel = "//span[@class='error_errorpage']";

    public static final String breadCrumbsForumLinkSel = "//ul[@class='breadcrumbs']//a[@href='" + JCommuneSeleniumTest.contextPath + "/sections']";

    public static final String iconLinkToMainPageSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "']";

    public static final String recentActivityLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/topics/recent']";

    public static final String messagesCountSel = "messages count";

    public static final String usersCountSel = "users count";

    public static final String usersOnlineCountSel = "visitors count";

    public static final String registeredUsersOnlineCountSel = "registered users count";

    public static final String guestsUsersOnlineCountSel = "guests count";

    public static final String profileLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/user' and not(@class='currentusername')]";

    @FindBy(xpath = profileLinkSel)
    private WebElement profileLink;

    @FindBy(xpath = loginLinkSel)
    private WebElement loginLink;

    @FindBy(xpath = currentUsernameLinkSel)
    private WebElement currentUsernameLink;

    @FindBy(xpath = errorPageSel)
    private WebElement errorPage;

    @FindBy(xpath = breadCrumbsForumLinkSel)
    private WebElement breadCrumbsForumLink;

    @FindBy(xpath = iconLinkToMainPageSel)
    private WebElement iconLinkToMainPage;

    @FindBy(xpath = recentActivityLinkSel)
    private WebElement recentActivityLink;

    @FindBy(id = messagesCountSel)
    private WebElement messagesCount;

    @FindBy(id = usersCountSel)
    private WebElement usersCount;

    @FindBy(id = usersOnlineCountSel)
    private WebElement usersOnlineCount;

    @FindBy(id = registeredUsersOnlineCountSel)
    private WebElement registeredUsersOnlineCount;

    @FindBy(id = guestsUsersOnlineCountSel)
    private WebElement guestsUsersOnlineCount;


    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Getters


    public WebElement getProfileLink() {
        return profileLink;
    }

    public WebElement getLoginLink() {
        return loginLink;
    }

    public WebElement getCurrentUsernameLink() {
        return currentUsernameLink;
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
