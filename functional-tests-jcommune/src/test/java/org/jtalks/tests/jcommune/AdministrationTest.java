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
 * @author Yuliya
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
    public void enablingAdminMode_shouldPass() {
        mainPage.switchOnAdminMode();
    }

    //logo tests:

    @Test
    public void validLogo_shouldPass() {
        ForumSetting logo = new ForumSetting().withObject("1Kb.jpg");
        ForumSettings.uploadLogo(logo);

    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_SIZE_ERROR)
    public void tooLargeLogo_shouldFail() throws ValidationException {
        ForumSetting logo = new ForumSetting().withObject("10Kb.jpg");
        ForumSettings.uploadLogo(logo);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_FORMAT_ERROR)
    public void wrongFormatLogo_shouldFail() throws ValidationException {
        ForumSetting logo = new ForumSetting().withObject("1.txt");
        ForumSettings.uploadLogo(logo);
    }

    @Test
    public void resetLogo_shouldPass() {
        ForumSetting logo = new ForumSetting();
        ForumSettings.resetLogo(logo);
    }

    //favicon tests:

    @Test
    public void validFavIcon_shouldPass(){
        ForumSetting favicon = new ForumSetting().withObject("1Kb.jpg");
        ForumSettings.uploadFavIcon(favicon);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_SIZE_ERROR)
    public void tooLargeFavIcon_shouldFail() throws ValidationException {
        ForumSetting favicon = new ForumSetting().withObject("10Kb.jpg");
        ForumSettings.uploadFavIcon(favicon);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_IMAGE_FORMAT_ERROR)
    public void wrongFormatFavIcon_shouldFail() throws ValidationException {
        ForumSetting favicon = new ForumSetting().withObject("1.txt");
        ForumSettings.uploadFavIcon(favicon);
    }

    @Test
    public void resetFavIcon_shouldPass() {
        ForumSetting favicon = new ForumSetting();
        ForumSettings.resetFavIcon(favicon);
    }

    //forum title tests:

    @Test
    public void settingValidForumTitle_shouldPass(){
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(30));
        ForumSettings.setForumTitle(title);
    }

    @Test
    public void settingForumTitleMaxBoundary_shouldPass(){
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(100));
        ForumSettings.setForumTitle(title);
    }

    @Test
    public void settingForumTitleMinBoundary_shouldPass(){
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(1));
        ForumSettings.setForumTitle(title);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_TITLE_LENGTH_ERROR)
    public void exceedingForumTitleMaxBoundary_shouldFail() throws ValidationException {
        ForumSetting title = new ForumSetting().withTitle(randomAlphanumeric(101));
        ForumSettings.setForumTitle(title);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_TITLE_LENGTH_ERROR)
    public void emptyForumTitle_shouldFail() throws ValidationException {
        ForumSetting title = new ForumSetting().withTitle("");
        ForumSettings.setForumTitle(title);
    }

    //forum description tests:

    @Test
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

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void exceedingForumDescriptionMaxBoundary_shouldFail() throws ValidationException {
        ForumSetting desc = new ForumSetting().withDescription(randomAlphanumeric(256));
        ForumSettings.setForumDescription(desc);
    }

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void emptyForumDescription_shouldFail() throws ValidationException {
        ForumSetting desc = new ForumSetting().withDescription("");
        ForumSettings.setForumDescription(desc);
    }

    //pages prefix tests:

    @Test
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

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void exceedingPagesPrefixMaxBoundary_shouldFail() throws ValidationException {
        ForumSetting prefix = new ForumSetting().withPrefix(randomAlphanumeric(256));
        ForumSettings.setPrefix(prefix);
    }

   //logo tooltip tests:

    @Test
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

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void exceedingLogoTooltipMaxBoundary_shouldFail() throws ValidationException {
        ForumSetting tooltip = new ForumSetting().withTooltip(randomAlphanumeric(256));
        ForumSettings.setLogoTooltip(tooltip);
    }

    //copyright tests:

    @Test
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

    @Test (expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = ForumSettingsDialog.WRONG_FIELD_LENGTH_ERROR)
    public void exceedingCopyrightMaxBoundary_shouldFail() throws ValidationException {
        ForumSetting copyright = new ForumSetting().withCopyright(randomAlphanumeric(256));
        ForumSettings.setCopyright(copyright);
    }

}