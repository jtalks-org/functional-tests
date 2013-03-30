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

import org.jtalks.tests.jcommune.webdriver.Users;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignInUserException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.logOut;

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
    public void correctUsernameAndPassword(String username, String password) throws Exception {
        Users.signInByDialog(username, password);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    @Parameters({"iuUsername", "uPassword"})
    public void incorrectUsername(String username, String password) throws Exception {
        Users.signInByDialog(username, password);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    @Parameters({"uUsername", "iuPassword"})
    public void incorrectPassword(String username, String password) throws Exception {
        Users.signInByDialog(username, password);
    }

    @Test(expectedExceptions = CouldNotSignInUserException.class)
    @Parameters({"iuUsername", "iuPassword"})
    public void incorrectUsernameAndPassword(String username, String password) throws Exception {
        Users.signInByDialog(username, password);
    }

    @Test
    @Parameters({"uUsername", "uPassword"})
    public void logInIsCaseInsensitive(String username, String password) throws Exception {
        Users.signInByDialog(username.toUpperCase(), password);
    }

}
