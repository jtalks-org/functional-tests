package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.ForumSettings;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.forumsetting.ForumSetting;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Yuliya
 */
@Test(groups = "htmlunit-incompatible")
public class AdministrationTest {

    @BeforeClass(alwaysRun = true)
    @Parameters({"appUrl"})
    public void signInAsAdmin(String appUrl) throws Exception {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
        Users.signIn(User.admin());
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void destroyCase(String appUrl) {
        driver.get(appUrl);//in case some dialogs are not closed
    }

    @Test(groups = "ui-tests")
    public void enablingAdminMode_shouldPass() {
        mainPage.switchOnAdminMode();
    }

    //logo tests:

    @Test(groups = "ui-tests")
    public void resetLogo_shouldPass() {
        ForumSettings.resetLogo();
    }

    //favicon tests:

    @Test(groups = "ui-tests")
    public void resetFavIcon_shouldPass() {
        ForumSettings.resetFavIcon();
    }

    //forum title tests:

    @Test(enabled = false, groups = "ui-tests") // bug with external links editor: http://jira.jtalks.org/browse/JC-994
    public void settingValidForumTitle_shouldPass(){
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(30));
        ForumSettings.setForumTitle(title);
    }

    @Test(enabled = false) // bug with external links editor: http://jira.jtalks.org/browse/JC-994
    public void settingForumTitleMaxBoundary_shouldPass(){
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(100));
        ForumSettings.setForumTitle(title);
    }

    @Test
    public void settingForumTitleMinBoundary_shouldPass(){
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(1));
        ForumSettings.setForumTitle(title);
    }

    @Test(groups = "ui-tests")
    public void exceedingForumTitleMaxBoundary_shouldFail(){
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(101));
        ForumSettings.setForumTitle(title);
        ForumSettings.assertErrorVisible();
    }

    @Test
    public void emptyForumTitle_shouldFail(){
        ForumSetting title = new ForumSetting().withTitle("");
        ForumSettings.setForumTitle(title);
        ForumSettings.assertErrorVisible();
    }

    //forum description tests:

    @Test(groups = "ui-tests")
    public void settingValidForumDescription_shouldPass(){
        ForumSetting desc = new ForumSetting().withDescription(randomAlphanumeric(30));
        ForumSettings.setForumDescription(desc);
    }

    @Test
    public void settingForumDescriptionMaxBoundary_shouldPass(){
        ForumSetting desc = new ForumSetting().withDescription(randomAlphanumeric(255));
        ForumSettings.setForumDescription(desc);
    }

    @Test
    public void settingForumDescriptionMinBoundary_shouldPass(){
        ForumSetting desc = new ForumSetting().withDescription(randomAlphanumeric(1));
        ForumSettings.setForumDescription(desc);
    }

    @Test
    public void exceedingForumDescriptionMaxBoundary_shouldFail(){
        ForumSetting desc = new ForumSetting().withDescription(randomAlphanumeric(256));
        ForumSettings.setForumDescription(desc);
        ForumSettings.assertErrorVisible();
    }

    @Test(groups = "ui-tests")
    public void emptyForumDescription_shouldFail(){
        ForumSetting desc = new ForumSetting().withDescription("");
        ForumSettings.setForumDescription(desc);
        ForumSettings.assertErrorVisible();
    }

    //pages prefix tests:

    @Test(groups = "ui-tests")
    public void settingValidPagesPrefix_shouldPass(){
        ForumSetting prefix = new ForumSetting().withPrefix(randomAlphanumeric(30));
        ForumSettings.setPrefix(prefix);
    }

    @Test
    public void settingPagesPrefixMaxBoundary_shouldPass(){
        ForumSetting prefix = new ForumSetting().withPrefix(randomAlphanumeric(255));
        ForumSettings.setPrefix(prefix);
    }

    @Test
    public void emptyPagesPrefix_shouldPass(){
        ForumSetting prefix = new ForumSetting().withPrefix("");
        ForumSettings.setPrefix(prefix);
    }

    @Test(groups = "ui-tests")
    public void exceedingPagesPrefixMaxBoundary_shouldFail(){
        ForumSetting prefix = new ForumSetting().withPrefix(randomAlphanumeric(256));
        ForumSettings.setPrefix(prefix);
        ForumSettings.assertErrorVisible();
    }

   //logo tooltip tests:

    @Test(groups = "ui-tests")
    public void settingValidLogoTooltip_shouldPass(){
        ForumSetting tooltip = new ForumSetting().withTooltip(randomAlphanumeric(30));
        ForumSettings.setLogoTooltip(tooltip);
    }

    @Test
    public void settingLogoTooltipMaxBoundary_shouldPass(){
        ForumSetting tooltip = new ForumSetting().withTooltip(randomAlphanumeric(255));
        ForumSettings.setLogoTooltip(tooltip);
    }

    @Test
    public void emptyLogoTooltip_shouldPass(){
        ForumSetting tooltip = new ForumSetting().withTooltip("");
        ForumSettings.setLogoTooltip(tooltip);
    }

    @Test(groups = "ui-tests")
    public void exceedingLogoTooltipMaxBoundary_shouldFail(){
        ForumSetting tooltip = new ForumSetting().withTooltip(randomAlphanumeric(256));
        ForumSettings.setLogoTooltip(tooltip);
        ForumSettings.assertErrorVisible();
    }

    //copyright tests:

    @Test(groups = "ui-tests")
    public void settingValidCopyright_shouldPass(){
        ForumSetting copyright = new ForumSetting().withCopyright(randomAlphanumeric(30));
        ForumSettings.setCopyright(copyright);
    }

    @Test
    public void settingCopyrightMaxBoundary_shouldPass(){
        ForumSetting copyright = new ForumSetting().withCopyright(randomAlphanumeric(255));
        ForumSettings.setCopyright(copyright);
    }

    @Test
    public void emptyCopyright_shouldPass(){
        ForumSetting copyright = new ForumSetting().withCopyright("");
        ForumSettings.setCopyright(copyright);
    }

    @Test(groups = "ui-tests")
    public void exceedingCopyrightMaxBoundary_shouldFail(){
        ForumSetting copyright = new ForumSetting().withCopyright(randomAlphanumeric(256));
        ForumSettings.setCopyright(copyright);
        ForumSettings.assertErrorVisible();
    }

    // branch visibility test

    @Test(groups = "ui-tests")
    public void branchWithoutViewTopicsPermissionIsVisibleInAdminMode_shouldPass() {
        mainPage.switchOffAdminMode();
        Branches.assertBranchIsInvisible("Invisible Branch");
        mainPage.switchOnAdminMode();
        Branches.assertBranchIsVisible("Invisible Branch");
    }

}
