package org.jtalks.tests.jcommune.webdriver.action;

import junit.framework.AssertionFailedError;
import org.jtalks.tests.jcommune.webdriver.entity.forumsetting.ForumSetting;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.forumSettingsDialog;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Yuliya
 */
public class ForumSettings {

    @Step
    public static void resetLogo(){
        info("Resetting logo");
        openForumSettingsDialog();
        forumSettingsDialog.clickRemoveLogoButton();
        sleep(500);
        forumSettingsDialog.clickSubmitRestoreButton();
        forumSettingsDialog.clickSaveChangesButton();
    }

    @Step
    public static void resetFavIcon(){
        info("Resetting favicon");
        openForumSettingsDialog();
        forumSettingsDialog.clickRemoveIconButton();
        sleep(500);
        forumSettingsDialog.clickSubmitRestoreButton();
        forumSettingsDialog.clickSaveChangesButton();
    }

    @Step
    public static void setForumTitle(ForumSetting forumSetting){
        info("Editing the Forum Title");
        openForumSettingsDialog();
        forumSettingsDialog.fillTitleField(forumSetting.getTitle());
        forumSettingsDialog.clickSaveChangesButton();
    }

    @Step
    public static void setForumDescription(ForumSetting forumSetting){
        info("Editing the Forum Description");
        openForumSettingsDialog();
        forumSettingsDialog.fillDescField(forumSetting.getDescription());
        forumSettingsDialog.clickSaveChangesButton();
    }

    @Step
    public static void setPrefix(ForumSetting forumSetting){
        info("Editing the Pages' Prefix");
        openForumSettingsDialog();
        forumSettingsDialog.fillPrefixField(forumSetting.getPrefix());
        forumSettingsDialog.clickSaveChangesButton();
    }

    @Step
    public static void setLogoTooltip(ForumSetting forumSetting){
        info("Editing the Logo Tooltip");
        openForumSettingsDialog();
        forumSettingsDialog.fillTooltipField(forumSetting.getTooltip());
        forumSettingsDialog.clickSaveChangesButton();
    }

    @Step
    public static void setCopyright(ForumSetting forumSetting){
        info("Editing the Forum Copyright");
        openForumSettingsDialog();
        forumSettingsDialog.fillCopyrightField(forumSetting.getCopyright());
        forumSettingsDialog.clickSaveChangesButton();
    }

    @Step
    public static boolean assertErrorVisible(){
        info("Checking whether the error has appeared");
        WebElement error = forumSettingsDialog.getErrorLine();
        if (error.isDisplayed()) {
            return true;
        }
        throw new AssertionFailedError("The error is not present on the page");
    }

    @Step
    private static void openForumSettingsDialog(){
        info("Opening Forum Settings dialog");
        WebElement dialog = mainPage.getForumSettingsDialog();
        boolean visible;
        try {
            visible = dialog.isDisplayed();
        } catch (NoSuchElementException e) {
            visible = false;
        }
        if (!visible) {
            mainPage.switchOnAdminMode();
            mainPage.pressOpenForumSettingsDialog();
            sleep(500);
        }
        driver.manage().window().maximize(); //Because forum settings' popup is not scrollable
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
