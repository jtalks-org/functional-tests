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

import config.SeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.net.URL;


/**
 * Created by IntelliJ IDEA.
 * User: ctapobep
 * Date: 12/17/11
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test1 {
	WebDriver driver;


	@BeforeSuite
	public void setupBeforeSuite() throws Exception {

		System.out.println();
		driver = new RemoteWebDriver(
				new URL(SeleniumConfig.getRemoteSeleniumServerUrl()),
				SeleniumConfig.getBrowserDriver());
	}

	@AfterSuite
	public void setupAfterSuite() {
		driver.close();
	}

	@Test
	public void launchSite() {
		driver.get("http://deploy.jtalks.org/jcommune");
	}

}
