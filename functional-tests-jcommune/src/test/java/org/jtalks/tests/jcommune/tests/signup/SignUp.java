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

package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.User;
import org.jtalks.tests.jcommune.common.UserActions;
import org.jtalks.tests.jcommune.exceptions.CouldNotSignInUserException;
import org.jtalks.tests.jcommune.exceptions.CouldNotSignUpUserException;
import org.testng.annotations.*;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

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
        User user = UserActions.signUpNewUserByDialog();
        UserActions.openActivationLink(user.getEmail());
        UserActions.signInByPage(user.getUsername(), user.getPassword());
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    public void signUpWithoutActivation() throws CouldNotSignInUserException, CouldNotSignUpUserException {
        User user = UserActions.signUpNewUserByDialog();
        UserActions.signInByDialog(user.getUsername(), user.getPassword());
    }
}
