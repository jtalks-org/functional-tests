package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;

/** @author stanislav bashkirtsev */
public class Pages {
    public static MainPage mainPage;
    public static SignInPage signInPage;
    public static SignUpPage signUpPage;
    public static TopicPage topicPage;
    public static PostPage postPage;
    public static SectionPage sectionPage;
    public static BranchPage branchPage;
    public static PrivateMessagesPage pmPage;
    public static ProfilePage profilePage;
    public static ExternalLinksDialog externalLinksDialog;
    public static ForumSettingsDialog forumSettingsDialog;


    public static void createAllPages(WebDriver driver) {
        mainPage = new MainPage(driver);
        signInPage = new SignInPage(driver);
        signUpPage = new SignUpPage(driver);
        topicPage = new TopicPage(driver);
        postPage = new PostPage(driver);
        sectionPage = new SectionPage(driver);
        branchPage = new BranchPage(driver);
        pmPage = new PrivateMessagesPage(driver);
        profilePage = new ProfilePage(driver);
        externalLinksDialog = new ExternalLinksDialog(driver);
        forumSettingsDialog = new ForumSettingsDialog(driver);
    }
}
