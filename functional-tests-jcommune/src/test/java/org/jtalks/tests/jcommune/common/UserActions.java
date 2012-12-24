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

package org.jtalks.tests.jcommune.common;

import org.jtalks.tests.jcommune.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.exceptions.CouldNotSignInUserException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signInPage;

/**
 * Contain user actions like sign in, sign out etc.
 *
 * @author Guram Savinov
 */
public class UserActions {

    /**
     * Sign in by userName and password.
     *
     * @param userName the userName
     * @param password the password
     * @throws org.jtalks.tests.jcommune.exceptions.CouldNotOpenPageException
     * @throws org.jtalks.tests.jcommune.exceptions.CouldNotSignInUserException
     * @return the {@code User} instance
     */
    public static User signIn(String userName, String password)
            throws CouldNotOpenPageException, CouldNotSignInUserException {
        mainPage.getLoginLink().click();
        try {
            driver.findElement(By.id(signInPage.signInFormSel));
        } catch (NoSuchElementException e) {
            throw new CouldNotOpenPageException("sign in form");
        }

        User user = new User(userName, password);
        signInPage.getUsernameField().sendKeys(user.getUserName());
        signInPage.getPasswordField().sendKeys(user.getPassword());
        signInPage.getSubmitButton().click();
        try {
            driver.findElement(By.xpath(mainPage.currentUsernameLinkSel));
        } catch (NoSuchElementException e) {
            throw new CouldNotSignInUserException(user);
        }

        return user;
    }

}
