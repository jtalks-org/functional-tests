package org.jtalks.tests.jcommune;

import org.apache.commons.lang3.RandomStringUtils;
import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Post;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.random;
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
        Users.viewProfile();
    }

    @Test
    public void editProfileWithNoChanges_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile(user);
        Users.assertProfileEquals(user);
    }


    @Test
    public void firstNameBetweenZeroAnd45Chars_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void veryLongFirstName_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setFirstName(randomAlphanumeric(46));
        Users.editProfile(user);
    }

    @Test
    public void lastNameBetweenZeroAnd255Chars_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(150));
        Users.editProfile(user);
    }

    @Test
    public void veryLongLastName_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(256));
        Users.editProfile(user);
    }

    @Test
    public void signatureBetweenZeroAnd255Chars_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(255));
        Users.editProfile(user);
    }

    @Test
    public void veryLongSignature_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(256));
        Users.editProfile(user);
    }

    @Test
    public void validUsualEmail_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "@" + "jtalks");
        Users.editProfile(user);
    }

    @Test
    public void emailWithOneDot_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + ".common@" + "jtalks");
        Users.editProfile(user);
    }

    @Test
    public void emailWithDots_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "test.test.test@" + "jtalks");
        Users.editProfile(user);
    }

    @Test
    public void emailWithDotAndPlus_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + ".test+test@" + "jtalks");
        Users.editProfile(user);
    }

    @Test
    public void emailWithDotAndDash_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "test.test-test@" + "jtalks");
        Users.editProfile(user);
    }

    @Test
    public void emailWithQuotesAndDot_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(8) + "\"test.test\"@" + "jtalks");
        Users.editProfile(user);
    }

    @Test
    public void veryLongEmail_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(40));
        Users.editProfile(user);
    }

    @Test
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
    public void enterValidDataInFieldLocation_shouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(30));
        Users.editProfile(user);
    }

    @Test
    public void enterVeryLongLocation_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(31));
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordWithoutConfirmNewPasswordAndCurrentPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndConfirmNewPasswordWithoutCurrentPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndConfirmNewPasswordAndCurrentPassword_shouldPass()	throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        user.setCurrentPassword(user.getPassword());
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndCurrentPasswordAndIncorrectConfirmPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(user.getNewPassword());
        user.setConfirmPassword(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndConfirmPasswordAndIncorrectCurrentPassword_shouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editProfile(user);
    }
}
