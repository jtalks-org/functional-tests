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

import org.jtalks.tests.jcommune.webdriver.User;
import org.jtalks.tests.jcommune.webdriver.Users;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignInUserException;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignUpUserException;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.*;
import org.testng.annotations.*;

/**
 * @author Guram Savinov
 */
public class SignUp {

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
	}

    @AfterMethod
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test
    public void signUpWithActivation() throws CouldNotSignUpUserException, CouldNotSignInUserException {
        User user = Users.signUpNewUserByDialog();
        Users.openActivationLink(user.getEmail());
        Users.signInByPage(user.getUsername(), user.getPassword());
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void signUpWithoutActivation() throws CouldNotSignInUserException, CouldNotSignUpUserException {
        User user = Users.signUpNewUserByDialog();
        Users.signInByDialog(user.getUsername(), user.getPassword());
    }
}
