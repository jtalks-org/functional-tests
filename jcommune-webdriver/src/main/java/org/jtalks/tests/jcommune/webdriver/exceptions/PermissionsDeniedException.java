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

package org.jtalks.tests.jcommune.webdriver.exceptions;

import org.jtalks.tests.jcommune.webdriver.User;

/**
 * This exception should be thrown when user haven't appropriate permissions for his action
 *
 * @author Guram Savinov
 */
public class PermissionsDeniedException extends Exception {
    public PermissionsDeniedException(User user) {
        super("User with username: '" + user.getUsername() + "' haven't permissions for current action");
    }
}
