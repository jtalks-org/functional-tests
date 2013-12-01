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

package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.entity.privatemessage.PrivateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;

/**
 * Contains actions for private messages
 *
 * @author Varro
 */
public class PrivateMessages {
    private static final Logger LOGGER = LoggerFactory.getLogger(Topics.class);

    public static PrivateMessage createPrivateMessage(PrivateMessage pm) {
        openPrivateMessagesPage();
        clickComposeMessage();
        fillPrivateMessageFields(pm);
        return pm;
    }

    private static void fillPrivateMessageFields(PrivateMessage pm) {
        pmPage.getToField().sendKeys(pm.getReceiver().getUsername());
        pmPage.getTitleField().sendKeys(pm.getMessageTopic());
        pmPage.getTitleField().sendKeys(pm.getMessageContent());
    }

    private static void clickComposeMessage() {
        pmPage.getPmNewMessageLink().click();
    }

    private static void openPrivateMessagesPage() {
        mainPage.openPrivateMessages();
    }
}
