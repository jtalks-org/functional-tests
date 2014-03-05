package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.ProfilePage;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
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
        Users.editProfile(user);
        Users.assertProfileEquals(user);
    }

    @Test
    public void firstNameWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test(expectedExceptions = ValidationException.class,
           expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_FIRST_NAME)
    public void veryLongFirstName_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName(randomAlphanumeric(46));
        Users.editProfile(user);
    }

    @Test
    public void lastNameWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(150));
        Users.editProfile(user);
    }

    @Test(expectedExceptions = ValidationException.class,
           expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_LAST_NAME)
    public void veryLongLastName_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(256));
        Users.editProfile(user);
    }

    @Test
    public void signatureWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(255));
        Users.editProfile(user);
    }

    @Test(expectedExceptions = ValidationException.class,
           expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_SIGNATURE)
    public void veryLongSignature_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(256));
        Users.editProfile(user);
    }

    @Test
    public void validUsualEmail_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "@" + "jtalks.org");
        Users.editProfile(user);
    }

    @Test
    public void emailWithOneDotInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + ".common@" + "jtalks.org");
        Users.editProfile(user);
    }

    @Test
    public void emailWithMultipleDotInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "test.test.test@" + "jtalks.org");
        Users.editProfile(user);
    }

    @Test
    public void emailWithDotAndPlusInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + ".test+test@" + "jtalks.org");
        Users.editProfile(user);
    }

    @Test
    public void emailWithDotAndDashInAddress_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "test.test-test@" + "jtalks.org");
        Users.editProfile(user);
    }

    @Test(expectedExceptions = ValidationException.class,
           expectedExceptionsMessageRegExp = ProfilePage.INVALID_EMAIL)
    public void emailWithQuotesAndDotInAddress_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "\"test.test\"@" + "jtalks.org");
        Users.editProfile(user);
    }

    @Test(expectedExceptions = ValidationException.class,
           expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_EMAIL)
    public void veryLongEmail_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(40));
        Users.editProfile(user);
    }

    @Test(expectedExceptions = ValidationException.class,
           expectedExceptionsMessageRegExp = ProfilePage.EMPTY_EMAIL)
    public void emptyFieldEmail_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail("");
        Users.editProfile(user);
    }

    @Test
    public void changePageSizeTo15_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setPageSize(15);
        Users.editProfile(user);
    }

    @Test
    public void changePageSizeTo25_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setPageSize(25);
        Users.editProfile(user);
    }

    @Test
    public void changePageSizeTo50_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setPageSize(50);
        Users.editProfile(user);
    }

    @Test
    public void locationWithAllowedLength_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(30));
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_LOCATION)
    public void enterVeryLongLocation_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(31));
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndConfirmNewPasswordAndCurrentPassword_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        user.setCurrentPassword(user.getPassword());
        Users.editProfile(user);
    }

    @Test
    public void enterCurrentPasswordWithoutNewPassword_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword("");
        user.setConfirmPassword("");
        user.setCurrentPassword(user.getPassword());
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CURRENT_PASSWORD + ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void enterNewPasswordWithoutConfirmNewPasswordAndCurrentPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CURRENT_PASSWORD + ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void enterNewPasswordAndConfirmNewPasswordWithoutCurrentPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void enterNewPasswordAndCurrentPasswordAndIncorrectConfirmPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(user.getNewPassword());
        user.setConfirmPassword(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CURRENT_PASSWORD)
    public void enterNewPasswordAndConfirmPasswordAndIncorrectCurrentPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void enterNewPasswordAndCurrentPasswordWithoutConfirmPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword("");
        user.setCurrentPassword(user.getPassword());
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void enterCurrentPasswordAndConfirmPasswordWithoutNewPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword("");
        user.setConfirmPassword(randomAlphanumeric(25));
        user.setCurrentPassword(user.getPassword());
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.WRONG_CONFIRMATION_PASSWORD)
    public void enterConfirmPasswordWithoutNewPasswordAndCurrentPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword("");
        user.setConfirmPassword(randomAlphanumeric(25));
        user.setCurrentPassword("");
        Users.editProfile(user);
    }

    @Test (expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = ProfilePage.TOO_LONG_PASSWORD)
    public void enterCurrentPasswordAndVeryLongNewPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(51));
        user.setConfirmPassword(user.getNewPassword);
        user.setCurrentPassword(user.getPassword());
        Users.editProfile(user);
    }
}