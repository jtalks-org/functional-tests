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

package org.jtalks.tests.jcommune.mail.mailtrap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;

/**
 * @author Guram Savinov
 */
public class MailtrapTest {

    private static final String SELENIUM_SERVER_URL = "http://autotest.jtalks.org:4444/wd/hub";

    public static void main(String[] args) throws IOException {
        WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_SERVER_URL), DesiredCapabilities.htmlUnit());
        String link = MailtrapMail.getActivationLink("P_w4a7h0ptao@jtalks.org");
        System.out.println(link);
        driver.quit();
    }
}