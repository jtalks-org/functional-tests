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

package org.jtalks.tests.jcommune.webdriver.entity.user;

/**
 * Contains JCommune user sign-up form fields: all {@code User} fields and password confirmation
 *
 * @author Guram Savinov
 */
public class UserForRegistration extends User {
    private String passwordConfirmation = super.getPassword();//same as password by default
    private boolean captchaIsLoaded = true;

    public UserForRegistration() {
    }

    public boolean getCaptchaState() {
        return captchaIsLoaded;
    }

    public void setCaptchaState(boolean captchaIsLoaded) {
        this.captchaIsLoaded = captchaIsLoaded;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * Set the value to be specified in sign up dialog for the password confirmation, user won't pass registration if
     * password confirmation does not match the password. By default it takes value from {@link #getPassword()}. It
     * makes sense to change it only if you'd like to check validation.
     * @param passwordConfirmation a confirmation of the password to be specified into sign up window
     */
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public static UserForRegistration withUsername(String username) {
        UserForRegistration user = new UserForRegistration();
        user.setUsername(username);
        return user;
    }

    public static UserForRegistration withEmail(String email) {
        UserForRegistration user = new UserForRegistration();
        user.setEmail(email);
        return user;
    }
}
