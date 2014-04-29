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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Jcommune user representation. Contains username, password and e-mail. If nothing is changed, all the fields
 * take random value.
 *
 * @author Guram Savinov
 */
public class User {
    private String username = RandomStringUtils.randomAlphanumeric(25);
    private String email = username + "@jtalks.org";
    private String password = username;
    private String firstName = "";
    private String lastName = "";
    private String signature = "";
    private int pageSize = 15;
    private String location = "";
    private String newPassword = "";
    private String confirmPassword = "";
    private String currentPassword = "";
    private boolean autoSubscribe = false;
    private boolean notifyIfSomeoneMentionsYou = false;
    private boolean notifyIfPrivateMessageIsReceived = false;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email) {
        this(username, password);
        this.email = email;
    }

    public static User admin() {
        return new User("admin", "admin", "admin@jtalks.org");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public boolean getAutoSubscribe() {
        return autoSubscribe;
    }

    public User setAutoSubscribe(boolean autoSubscribe) {
        User user = new User();
        user.autoSubscribe = autoSubscribe;
        return user;
    }

    public boolean getNotifyIfSomeoneMentionsYou() {
        return notifyIfSomeoneMentionsYou;
    }

    public User setNotifyIfSomeoneMentionsYou(boolean notifyIfSomeoneMentionsYou) {
        User user = new User();
        user.notifyIfSomeoneMentionsYou = notifyIfSomeoneMentionsYou;
        return user;
    }

    public boolean getNotifyIfPrivateMessageIsReceived() {
        return notifyIfPrivateMessageIsReceived;
    }

    public User setNotifyIfPrivateMessageIsReceived() {
        User user = new User();
        user.notifyIfPrivateMessageIsReceived = notifyIfPrivateMessageIsReceived;
        return user;
    }

    @Override
    public String toString() {
        return "[username='" + username + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ']';
    }
}
