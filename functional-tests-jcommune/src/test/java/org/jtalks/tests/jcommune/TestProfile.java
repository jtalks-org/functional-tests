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
    public void viewUserProfile_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.viewProfile(user);
    }

    @Test
    public void editProfileWithNoChanges_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile(user);
        Users.assertProfileEquals(user);
    }


    @Test
    public void firstNameBetweenZeroAnd45Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterValidFirstName();
        Profile.saveChanges();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void veryLongFirstName_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterVeryLongFirstName();
        Profile.saveChanges();
    }

    @Test
    public void lastNameBetweenZeroAnd255Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterValidLastName();
        Profile.saveChanges();
    }

    @Test
    public void veryLongLastName_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterVeryLongLastName();
        Profile.saveChanges();
    }

    @Test
    public void signatureBetweenZeroAnd255Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterValidSignature();
        Profile.saveChanges();
    }

    @Test
    public void veryLongSignature_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterVeryLongSignature();
        Profile.saveChanges();
    }

    @Test
    public void validEmail_ShouldPass() throws Exception {
    }

    @Test
    public void veryLongEmail_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterVeryLongEmail();
        Profile.saveChanges();
    }

    @Test
    public void incorrectEmailWithSpecialSymbols_ShouldFail throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterIncorrectEmailWithSpecailSymbols();
        Profile.saveChanges();
    }

    @Test
    public void emptyFieldEmail_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.deletDataInFieldEmail();
        Profile.saveChanges();
    }

    @Test
    public void changePageSizeAndCheckMessageInTheTopic() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic(new Post, "text1"));
        for (int i = 0; i < 3; i++) {
            Users.editProfile();
            int postCount = Profile.changePageSize();
            Profile.saveChanges();
            Branches.openBranch(newTopic.getBranch().getTitle());
            for (int j = postCount; j > 0; j++) {
                int count = Profile.checkPostInTopic(j);
                if (count == postCount) {
                    continue;
                }
                else {
                    System.out.println("Page size was choosen " + postCount + ",but in page was found " + count);
                }
            }
        }
    }

    @Test
    public void enterValidDataInFieldLocation_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterValidDataInFieldLocation();
        Profile.saveChanges();
    }

    @Test
    public void enterVeryLongLocation_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterVeryLongLocation();
        Profile.saveChanges();
    }

    @Test
    public void enterNewPasswordWithoutConfirmNewPasswordAndCurrentPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterNewPassword();
        Profile.saveChanges();
    }

    @Test
    public void enterNewPasswordAndConfirmNewPasswordWithoutCurrentPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.editProfile();
        Profile.enterNewPassword();
        Profile.enterConfirmPassword();
        Profile.saveChanges();
    }

    @Test
    public void enterNewPasswordAndConfirmNewPasswordAndCurrentPassword_ShouldPass()	throws Exception {
        User user = Users.signUpAndSignIn();
        String newPass = RandomStringUtils.randomAlphanumeric(25);
        Users.editProfile();
        Profile.enterNewPassword(newPass);
        Profile.enterConfirmPassword(newPass);
        Profile.enterCurrentPassword(user);
        Profile.saveChanges();
    }

    @Test
    public void enterNewPasswordAndCurrentPasswordAndIncorrectConfirmPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        String newPass = RandomStringUtils.randomAlphanumeric(25);
        Users.editProfile();
        Profile.enterNewPassword(newPass);
        Profile.enterConfirmPassword(RandomStringUtils.randomAlphanumeric(25));
        Profile.enterCurrentPassword(user);
        Profile.saveChanges();
    }

    @Test
    public void enterNewPasswordAndConfirmPasswordAndIncorrectCurrentPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        String newPass = RandomStringUtils.randomAlphanumeric(25);
        Users.editProfile();
        Profile.enterNewPassword(newPass);
        Profile.enterConfirmPassword(newPass);
        Profile.enterIncorrectCurrentPassword();
        Profile.saveChanges();
    }
}
