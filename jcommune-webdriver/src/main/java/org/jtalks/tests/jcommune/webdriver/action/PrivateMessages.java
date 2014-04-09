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

import junit.framework.AssertionFailedError;
import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.page.PrivateMessagesPage;
import org.jtalks.tests.jcommune.webdriver.entity.privatemessage.PrivateMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;
import static org.testng.Assert.assertTrue;

/**
 * Contains actions for private messages
 *
 * @author Varro
 */
public class PrivateMessages {
    private static final Logger LOGGER = LoggerFactory.getLogger(Topics.class);

    public static void sendPrivateMessage(PrivateMessage pm) {
        mainPage.openPrivateMessages();
        pmPage.clickComposeMessage();
        fillPrivateMessageFields(pm);
        pmPage.getSendButton().click();
    }

    private static void fillPrivateMessageFields(PrivateMessage pm) {
        pmPage.fillToField(pm.getReceiver().getUsername());
        pmPage.fillTitleField(pm.getMessageSubject());
        pmPage.fillMessageField(pm.getMessageContent());
    }

    public static boolean pmIsReceived(PrivateMessage pm) {
        mainPage.openPrivateMessages();
        info("Checking whether the private message subject is on the page");
        List<WebElement> pmList = pmPage.getPmList();

        for(WebElement singlePmRow : pmList) {
            if(singlePmRow.findElements(By.cssSelector("a")).get(1).getText().equals(pm.getMessageSubject())) {
                info("The private message was found!");
                return true;
            }
        }
        LOGGER.info("Expected private message not found: {}", pm);
        return false;
    }

    public static void removePm(PrivateMessage pm) {
        info("Removing Private Message: " + pm);
        List<WebElement> pmList = pmPage.getPmList();

        for(WebElement singlePmRow : pmList) {
            if(singlePmRow.findElements(By.cssSelector("a")).get(1).getText().equals(pm.getMessageSubject())) {
                info("The private message for deleting was found!");
                singlePmRow.findElement(By.cssSelector("input")).click();
                pmPage.getDelButton().click();
                pmPage.getOkButtonRemovingPmDialog().click();
                info("The private message was successfully deleted!");
                return;
            }
        }
        LOGGER.info("Expected private message not found: {}", pm);
        throw new AssertionFailedError("Can't delete private message because it's not present on the page: " + pm);
    }

    public static boolean assertPmReceived(User receiver, PrivateMessage pm) {
        Users.logout();
        Users.signIn(receiver);
        if(pmIsReceived(pm)) {
            return true;
        }
        throw new AssertionFailedError(String.format("The private message is not present on the page " +
                "(author - %1, receiver - %2, subject - %3)", pm.getAuthor(), pm.getReceiver(), pm.getMessageSubject()));
    }

}
