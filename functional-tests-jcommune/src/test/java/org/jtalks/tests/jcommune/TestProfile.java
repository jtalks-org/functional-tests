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
    public void viewUserProfile_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Users.viewProfile(user);
        Users.viewProfile();
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
    public void lastNameBetweenZeroAnd255Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(150));
        Users.editProfile(user);
    }

    @Test
    public void veryLongLastName_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLastName(randomAlphanumeric(256));
        Users.editProfile(user);
    }

    @Test
    public void signatureBetweenZeroAnd255Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(255));
        Users.editProfile(user);
    }

    @Test
    public void veryLongSignature_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setSignature(randomAlphanumeric(256));
        Users.editProfile(user);
    }

    @Test
    public void validEmail_ShouldPass() throws Exception {
    }

    @Test
    public void veryLongEmail_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail(randomAlphanumeric(40));
        Users.editProfile(user);
    }

    @Test
    public void incorrectEmailWithSpecialSymbols_ShouldFail throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail();  // I'm not sure when symbols I must use
        Users.editProfile(user);
    }

    @Test
    public void emptyFieldEmail_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setEmail("");
        Users.editProfile(user);
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
        user.setLocation(randomAlphanumeric(30));
        Users.editProfile(user);
    }

    @Test
    public void enterVeryLongLocation_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setLocation(randomAlphanumeric(31));
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordWithoutConfirmNewPasswordAndCurrentPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndConfirmNewPasswordWithoutCurrentPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndConfirmNewPasswordAndCurrentPassword_ShouldPass()	throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        user.setCurrentPassword(user.getPassword());
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndCurrentPasswordAndIncorrectConfirmPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(user.getNewPassword());
        user.setConfirmPassword(randomAlphanumeric(25));
        Users.editProfile(user);
    }

    @Test
    public void enterNewPasswordAndConfirmPasswordAndIncorrectCurrentPassword_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        user.setNewPassword(randomAlphanumeric(25));
        user.setCurrentPassword(randomAlphanumeric(25));
        user.setConfirmPassword(user.getNewPassword());
        Users.editProfile(user);
    }
}
