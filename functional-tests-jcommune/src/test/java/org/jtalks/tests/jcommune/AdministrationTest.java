package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.ForumSettings;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.forumsetting.ForumSetting;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.ForumSettingsDialog;
import org.testng.annotations.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * Created by Юлия on 22.03.14.
 */
public class AdministrationTest {

    @BeforeClass
    @Parameters({"appUrl"})
    public void signInAsAdmin(String appUrl) throws Exception {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
        Users.signIn(User.admin());
    }

    @BeforeMethod
    @Parameters({"appUrl"})
    public void destroyCase(String appUrl) {
        driver.get(appUrl);//in case some dialogs are not closed
    }

    @Test
    public void adminCanEnableAdministrationMode() {
        mainPage.switchOnAdminMode();
    }

    //logo tests:

    @Test
    public void adminCanUploadValidLogo() {
        ForumSetting logo = new ForumSetting().withObject("1Kb.jpg");
        ForumSettings.uploadLogo(logo);

    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_SIZE_ERROR)
    public void adminCannotUploadTooLargeLogo() throws ValidationException {
        ForumSetting logo = new ForumSetting().withObject("10Kb.jpg");
        ForumSettings.uploadLogo(logo);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_FORMAT_ERROR)
    public void adminCannotUploadWrongFormatLogo() throws ValidationException {
        ForumSetting logo = new ForumSetting().withObject("1.txt");
        ForumSettings.uploadLogo(logo);
    }

    @Test
    public void adminCanResetLogo() {
        ForumSetting logo = new ForumSetting();
        ForumSettings.resetLogo(logo);
    }

    //favicon tests:

    @Test
    public void adminCanUploadValidFavIcon(){
        ForumSetting favicon = new ForumSetting().withObject("1Kb.jpg");
        ForumSettings.uploadFavIcon(favicon);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_SIZE_ERROR)
    public void adminCannotUploadTooLargeFavIcon() throws ValidationException {
        ForumSetting favicon = new ForumSetting().withObject("10Kb.jpg");
        ForumSettings.uploadFavIcon(favicon);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_FORMAT_ERROR)
    public void adminCannotUploadWrongFormatFavIcon() throws ValidationException {
        ForumSetting favicon = new ForumSetting().withObject("1.txt");
        ForumSettings.uploadFavIcon(favicon);
    }

    @Test
    public void adminCanResetFavIcon() {
        ForumSetting favicon = new ForumSetting();
        ForumSettings.resetFavIcon(favicon);
    }

    //forum title tests:

    @Test
    public void adminCanEditValidForumTitle(){
        ForumSetting title = new ForumSetting();
        ForumSettings.editForumTitle(title);
    }

    @Test
    public void adminCanSaveMaxValueForumTitle(){
        ForumSetting title = new ForumSetting().withValue(randomAlphanumeric(100));
        ForumSettings.editForumTitle(title);
    }

    @Test
    public void adminCanSaveMinValueForumTitle(){
        ForumSetting title = new ForumSetting().withValue(randomAlphanumeric(1));
        ForumSettings.editForumTitle(title);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_TITLE_LENGTH_ERROR)
    public void adminCannotExceedMaxForumTitleLimit() throws ValidationException {
        ForumSetting title = new ForumSetting().withValue(randomAlphanumeric(101));
        ForumSettings.editForumTitle(title);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_TITLE_LENGTH_ERROR)
    public void adminCannotSaveEmptyForumTitle() throws ValidationException {
        ForumSetting title = new ForumSetting().withValue("");
        ForumSettings.editForumTitle(title);
    }

    //forum description tests:

    @Test
    public void adminCanEditValidForumDescription(){
        ForumSetting desc = new ForumSetting();
        ForumSettings.editForumDescription(desc);
    }

    @Test
    public void adminCanSaveMaxValueForumDescription(){
        ForumSetting desc = new ForumSetting().withValue(randomAlphanumeric(255));
        ForumSettings.editForumDescription(desc);
    }

    @Test
    public void adminCanSaveMinValueForumDescription(){
        ForumSetting desc = new ForumSetting().withValue(randomAlphanumeric(1));
        ForumSettings.editForumDescription(desc);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxForumDescriptionLimit() throws ValidationException {
        ForumSetting desc = new ForumSetting().withValue(randomAlphanumeric(256));
        ForumSettings.editForumDescription(desc);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotSaveEmptyForumDescription() throws ValidationException {
        ForumSetting desc = new ForumSetting().withValue("");
        ForumSettings.editForumDescription(desc);
    }

    //pages prefix tests:

    @Test
    public void adminCanEditValidPagesPrefix(){
        ForumSetting prefix = new ForumSetting();
        ForumSettings.editPrefix(prefix);
    }

    @Test
    public void adminCanSaveMaxValuePagesPrefix(){
        ForumSetting prefix = new ForumSetting().withValue(randomAlphanumeric(255));
        ForumSettings.editPrefix(prefix);
    }

    @Test
    public void adminCanSaveEmptyPagesPrefix(){
        ForumSetting prefix = new ForumSetting().withValue("");
        ForumSettings.editPrefix(prefix);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxPagesPrefixLimit() throws ValidationException {
        ForumSetting prefix = new ForumSetting().withValue(randomAlphanumeric(256));
        ForumSettings.editPrefix(prefix);
    }

   //logo tooltip tests:

    @Test
    public void adminCanEditValidLogoTooltip(){
        ForumSetting tooltip = new ForumSetting();
        ForumSettings.editLogoTooltip(tooltip);
    }

    @Test
    public void adminCanSaveMaxValueLogoTooltip(){
        ForumSetting tooltip = new ForumSetting().withValue(randomAlphanumeric(255));
        ForumSettings.editLogoTooltip(tooltip);
    }

    @Test
    public void adminCanSaveEmptyLogoTooltip(){
        ForumSetting tooltip = new ForumSetting().withValue("");
        ForumSettings.editLogoTooltip(tooltip);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxLogoTooltipLimit() throws ValidationException {
        ForumSetting tooltip = new ForumSetting().withValue(randomAlphanumeric(256));
        ForumSettings.editLogoTooltip(tooltip);
    }

    //copyright tests:

    @Test
    public void adminCanEditValidCopyright(){
        ForumSetting copyright = new ForumSetting();
        ForumSettings.editCopyright(copyright);
    }

    @Test
    public void adminCanSaveMaxValueCopyright(){
        ForumSetting copyright = new ForumSetting().withValue(randomAlphanumeric(255));
        ForumSettings.editCopyright(copyright);
    }

    @Test
    public void adminCanSaveEmptyLogoCopyright(){
        ForumSetting copyright = new ForumSetting().withValue("");
        ForumSettings.editCopyright(copyright);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxCopyrightLimit() throws ValidationException {
        ForumSetting copyright = new ForumSetting().withValue(randomAlphanumeric(256));
        ForumSettings.editCopyright(copyright);
    }

}