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

package org.jtalks.tests.jcommune.tests;

import org.jtalks.tests.jcommune.common.UserActions;
import org.jtalks.tests.jcommune.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.exceptions.CouldNotSignInUserException;
import org.testng.annotations.*;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;

/**
 * @author Guram Savinov
 */
public class SignIn {

    @BeforeMethod
    @Parameters("app-url")
    public void setup(String appUrl) {
        driver.get(appUrl);
    }

    @AfterMethod
    @Parameters("app-url")
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test
    @Parameters({"uUsername", "uPassword"})
    public void correctUsernameAndPassword(String userName, String password)
            throws CouldNotSignInUserException, CouldNotOpenPageException {
        UserActions.signIn(userName, password);
    }

    @Test
    @Parameters({""})
}
