/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.utils.StringHelp;
import org.jtalks.tests.jcommune.webdriver.User;
import org.jtalks.tests.jcommune.webdriver.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.Users;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignInUserException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.printSeleniumSessionId;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/** @author Guram Savinov */
public class SignUp {

	@BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
		driver.get(appUrl);
        mainPage.logOutIfLoggedIn();
        printSeleniumSessionId(getClass());
    }

    @Test
    public void signUpWithActivation() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void signUpWithoutActivation() throws Exception {
        User user = Users.signUpWithoutActivation();
        Users.signIn(user);
    }

    @Test
    public void validRegistration_JC_1() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString(8));
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail(StringHelp.getRandomEmail());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void emptyData_JC_2() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("");
        user.setPassword("");
        user.setPasswordConfirmation("");
        user.setEmail("");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void spaceAsUsername_JC_3() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(" ");
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail(StringHelp.getRandomEmail());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void tooLongUsername_JC_3() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString((26)));
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail(StringHelp.getRandomEmail());
        Users.signUp(user);
    }

    @Test
    public void usernameBetween1and25Chars_JC_4() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString(8));
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail(StringHelp.getRandomEmail());
        Users.signUp(user);
    }

    @Test
    public void usernameWithSpaces_JC_4() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString(3) + " " + StringHelp.getRandomString(4));
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail(StringHelp.getRandomEmail());
        Users.signUp(user);
    }

    @Test
    public void usernameMaxAllowChars_JC_4() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString(25));
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail(StringHelp.getRandomEmail());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidEmailFormat_JC_5() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString((8)));
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail(StringHelp.getRandomString(8) + "@" + "jtalks");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void emptyEmail_JC_5() throws Exception {
        String password = StringHelp.getRandomString(9);
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString((8)));
        user.setPassword(password);
        user.setPasswordConfirmation(password);
        user.setEmail("");
        Users.signUp(user);
    }
}
