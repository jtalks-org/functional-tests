package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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

    @AfterMethod
    @Parameters({"appUrl"})
    public void destroyCase(String appUrl) {
        driver.get(appUrl);//in case some dialogs are not closed
    }

    @Test
    public void adminCanEnableAdministrationMode() {
        mainPage.switchOnAdminMode();
    }

    @Test
    public void adminCanOpenForumSettingsDialog() {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickCancelChangesButton();
    }

    //logo tests:

    @Test
    public void adminCanUploadValidLogo() {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickUploadLogoButton();
        ForumSettingsDialog.chooseFile("1Kb.jpg");
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_SIZE_ERROR)
    public void adminCannotUploadTooLargeLogo() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickUploadLogoButton();
        ForumSettingsDialog.chooseFile("10Kb.jpg");
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_FORMAT_ERROR)
    public void adminCannotUploadWrongFormatLogo() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickUploadLogoButton();
        ForumSettingsDialog.chooseFile("1.txt");
    }

    @Test
    public void adminCanResetLogo() {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickRemoveLogoButton();
        ForumSettingsDialog.clickSubmitRemoveLogoButton();
        ForumSettingsDialog.clickSaveChangesButton();
    }

    //favicon tests:

    @Test
    public void adminCanUploadFavIcon(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickUploadFavIconButton();
        ForumSettingsDialog.chooseFile("1Kb.jpg");
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_SIZE_ERROR)
    public void adminCannotUploadTooLargeFavIcon() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickUploadFavIconButton();
        ForumSettingsDialog.chooseFile("10Kb.jpg");
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_FORMAT_ERROR)
    public void adminCannotUploadWrongFormatFavIcon() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickUploadFavIconButton();
        ForumSettingsDialog.chooseFile("1.txt");
    }

    @Test
    public void adminCanResetFavIcon() {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.clickRemoveFavIconButton();
        ForumSettingsDialog.clickSubmitRemoveFavIconButton();
        ForumSettingsDialog.clickSaveChangesButton();
    }

    //forum title tests:

    @Test
    public void adminCanEditValidForumTitle(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog title = new ForumSettingsDialog();
        ForumSettingsDialog.editForumTitle(title);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveMaxValueForumTitle(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog title = new ForumSettingsDialog().withValue(randomAlphanumeric(100));
        ForumSettingsDialog.editForumTitle(title);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveMinValueForumTitle(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog title = new ForumSettingsDialog().withValue(randomAlphanumeric(1));
        ForumSettingsDialog.editForumTitle(title);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_TITLE_LENGTH_ERROR)
    public void adminCannotExceedMaxForumTitleLimit() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog title = new ForumSettingsDialog().withValue(randomAlphanumeric(101));
        ForumSettingsDialog.editForumTitle(title);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_TITLE_LENGTH_ERROR)
    public void adminCannotSaveEmptyForumTitle() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog title = new ForumSettingsDialog().withValue("");
        ForumSettingsDialog.editForumTitle(title);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    //forum description tests:

    @Test
    public void adminCanEditValidForumDescription(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.editForumDescription();
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveMaxValueForumDescription(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog desc = new ForumSettingsDialog().withValue(randomAlphanumeric(255));
        ForumSettingsDialog.editForumDescription(desc);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveMinValueForumDescription(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog desc = new ForumSettingsDialog().withValue(randomAlphanumeric(1));
        ForumSettingsDialog.editForumDescription(desc);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxForumDescriptionLimit() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog desc = new ForumSettingsDialog().withValue(randomAlphanumeric(256));
        ForumSettingsDialog.editForumDescription(desc);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotSaveEmptyForumDescription() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog desc = new ForumSettingsDialog().withValue("");
        ForumSettingsDialog.editForumDescription(desc);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    //pages prefix tests:

    @Test
    public void adminCanEditValidPagesPrefix(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.editPrefix();
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveMaxValuePagesPrefix(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog prefix = new ForumSettingsDialog().withValue(randomAlphanumeric(255));
        ForumSettingsDialog.editPrefix(prefix);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveEmptyPagesPrefix(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog prefix = new ForumSettingsDialog().withValue("");
        ForumSettingsDialog.editPrefix(prefix);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxPagesPrefixLimit() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog prefix = new ForumSettingsDialog().withValue(randomAlphanumeric(256));
        ForumSettingsDialog.editPrefix(prefix);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    //logo tooltip tests:

    @Test
    public void adminCanEditValidLogoTooltip(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.editLogoTooltip();
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveMaxValueLogoTooltip(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog tooltip = new ForumSettingsDialog().withValue(randomAlphanumeric(255));
        ForumSettingsDialog.editLogoTooltip(tooltip);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveEmptyLogoTooltip(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog tooltip = new ForumSettingsDialog().withValue("");
        ForumSettingsDialog.editLogoTooltip(tooltip);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxLogoTooltipLimit() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog tooltip = new ForumSettingsDialog().withValue(randomAlphanumeric(256));
        ForumSettingsDialog.editLogoTooltip(tooltip);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    //copyright tests:

    @Test
    public void adminCanEditValidCopyright(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog.editCopyright();
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveMaxValueCopyright(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog copyright = new ForumSettingsDialog().withValue(randomAlphanumeric(255));
        ForumSettingsDialog.editCopyright(copyright);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test
    public void adminCanSaveEmptyLogoCopyright(){
        Administration.openForumSettingsDialog();
        ForumSettingsDialog copyright = new ForumSettingsDialog().withValue("");
        ForumSettingsDialog.editCopyright(copyright);
        ForumSettingsDialog.clickSaveChangesButton();
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void adminCannotExceedMaxCopyrightLimit() throws ValidationException {
        Administration.openForumSettingsDialog();
        ForumSettingsDialog copyright = new ForumSettingsDialog().withValue(randomAlphanumeric(256));
        ForumSettingsDialog.editCopyright(copyright);
        ForumSettingsDialog.clickSaveChangesButton();
    }

}



