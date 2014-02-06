package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * Created with IntelliJ IDEA.
 * User: Zalk
 * Date: 06.02.14
 * Time: 21:41
 * To change this template use File | Settings | File Templates.
 */
public class TestProfile {

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void openAndViewUserProfile_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToViewProfile();
    }

    @Test
    public void openAndEditUserProfile_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.saveChanges();
    }


    @Test
    public void enterValidFirstNameBetweenZeroAnd45Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterValidFirstName();
        Profile.saveChanges();
    }

    @Test
    public void enterVeryLongFirstName_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterVeryLongFirstName();
        Profile.saveChanges();
    }

    @Test
    public void enterValidLastNameBetweenZeroAnd255Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterValidLastName();
        Profile.saveChanges();
    }

    @Test
    public void enterVeryLongLastName_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterVeryLongLastName();
        Profile.saveChanges();
    }

    @Test
    public void enterValidSignatureBetweenZeroAnd255Chars_ShouldPass() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterValidSignature();
        Profile.saveChanges();
    }

    @Test
    public void enterVeryLongSignature_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterVeryLongSignature();
        Profile.saveChanges();
    }

    @Test
    public void enterValidEmail_ShouldPass() throws Exception {
    }

    @Test
    public void enterVeryLongEmail_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterVeryLongEmail();
        Profile.saveChanges();
    }

    @Test
    public void enterIncorrectEmailWithSpecialSymbols_ShouldFail throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.enterIncorrectEmailWithSpecailSymbols();
        Profile.saveChanges();
    }

    @Test
    public void emptyFieldEmail_ShouldFail() throws Exception {
        User user = Users.signUpAndSignIn();
        Profile.goToEditProfile();
        Profile.deletDataInFieldEmail();
        Profile.saveChanges();
    }
}
