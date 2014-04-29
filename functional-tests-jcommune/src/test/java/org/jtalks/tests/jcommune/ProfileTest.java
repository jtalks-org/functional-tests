package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.ProfilePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Igor Bogdanov
 */
public class ProfileTest {

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void viewUserProfile_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.viewProfile(user);
    }

    @Test
    public void editProfileWithNoChanges_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void firstNameWithMinBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName("");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void firstNameWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName(randomAlphanumeric(25));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void firstNameWithMaxBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName(randomAlphanumeric(45));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_FIRST_NAME)
    public void veryLongFirstName_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName(randomAlphanumeric(46));
        Users.editMainUserInfo(user);
    }

    @Test
    public void lastNameWithMinBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName("");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void lastNameWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(150));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void lastNameWithMaxBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(255));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_LAST_NAME)
    public void veryLongLastName_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(256));
        Users.editMainUserInfo(user);
    }

    @Test
    public void signatureWithMinBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature("");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void signatureWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(255));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void signatureWithMaxBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(255));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_SIGNATURE)
    public void veryLongSignature_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(256));
        Users.editMainUserInfo(user);
    }

    @Test
    public void emailWithMaxBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(39) + "@" + "jtalks.org");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void validUsualEmail_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "@" + "jtalks.org");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void emailWithOneDotInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + ".common@" + "jtalks.org");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void emailWithMultipleDotInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "test.test.test@" + "jtalks.org");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void emailWithDotAndPlusInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + ".test+test@" + "jtalks.org");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void emailWithDotAndDashInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "test.test-test@" + "jtalks.org");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.INVALID_EMAIL)
    public void emailWithQuotesAndDotInAddress_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "\"test.test\"@" + "jtalks.org");
        Users.editMainUserInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_EMAIL)
    public void veryLongEmail_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(40) + "@jtalks.org");
        Users.editMainUserInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.EMPTY_EMAIL)
    public void emptyFieldEmail_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail("");
        Users.editMainUserInfo(user);
    }

    @Test
    public void changePageSizeTo15_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setPageSize(15);
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void changePageSizeTo25_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setPageSize(25);
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void changePageSizeTo50_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setPageSize(50);
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void locationWithMinBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation("");
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void locationWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(15));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test
    public void locationWithMaxBoundaryValue_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(30));
        Users.editMainUserInfo(user);
        Users.assertMainUserInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_LOCATION)
    public void enterVeryLongLocation_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(31));
        Users.editMainUserInfo(user);
    }

    @Test
    public void shouldChangePassword_ifEnterOnlyCurrentPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword("");
        user.setConfirmPassword("");
        user.setCurrentPassword(user.getPassword());
        Users.editPasswordInfo(user);
    }

    @Test
    public void shouldChangePassword_ifCorrectCurrentAndConfirmPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        user.setCurrentPassword(user.getPassword());
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CURRENT_PASSWORD + ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void shouldNotChangePassword_withoutConfirmAndCurrentPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CURRENT_PASSWORD)
    public void shouldNotChangePassword_withoutCurrentPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void shouldNotChangePassword_withIncorrectConfirmPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(user.getPassword());
        user.setConfirmPassword(randomAlphanumeric(25));
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CURRENT_PASSWORD)
    public void shouldNotChangePassword_withIncorrectCurrentPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void shouldNotChangePassword_withoutConfirmPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword("");
        user.setCurrentPassword(user.getPassword());
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void shouldNotChangePassword_withoutNewPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword("");
        user.setConfirmPassword(randomAlphanumeric(25));
        user.setCurrentPassword(user.getPassword());
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void shouldNotChangePassword_withoutCurrentAndNewPassword() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword("");
        user.setConfirmPassword(randomAlphanumeric(25));
        user.setCurrentPassword("");
        Users.editPasswordInfo(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_PASSWORD)
    public void shouldNotChangePassword_ifNewPasswordTooLong() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(51));
        user.setConfirmPassword(user.getNewPassword());
        user.setCurrentPassword(user.getPassword());
        Users.editPasswordInfo(user);
    }
}