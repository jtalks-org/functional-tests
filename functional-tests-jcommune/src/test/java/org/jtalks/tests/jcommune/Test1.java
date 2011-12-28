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

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.HttpCommandProcessor;
import com.thoughtworks.selenium.Selenium;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static com.thoughtworks.selenium.SeleneseTestCase.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: ctapobep
 * Date: 12/17/11
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test1 {
	private SeleniumServer server;
	private HttpCommandProcessor proc;
	private Selenium selenium;

	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) {
	  String seleniumHost = "localhost";
	  String seleniumPort = "4447";
	  String seleniumBrowser = "*firefox";
	  String seleniumUrl = "http://deploy.jtalks.org";

	  RemoteControlConfiguration rcc = new RemoteControlConfiguration();
	  rcc.setSingleWindow(true);
	  rcc.setPort(Integer.parseInt(seleniumPort));

	  try {
		server = new SeleniumServer(false, rcc);
		server.boot();
	  } catch (Exception e) {
		throw new IllegalStateException("Can't start selenium server", e);
	  }

	  proc = new HttpCommandProcessor(seleniumHost, Integer.parseInt(seleniumPort),
		  seleniumBrowser, seleniumUrl);
	  selenium = new DefaultSelenium(proc);
	  selenium.start();
	}

	@AfterSuite(alwaysRun = true)
	public void setupAfterSuite() {
	  selenium.stop();
	  server.stop();
	}


	@Test(description="Launches the WordPress site")
	public void launchSite() throws Exception {
	selenium.open("http://deploy.jtalks.org/jcommune");
	selenium.waitForPageToLoad("30000");
	assertEquals(selenium.getTitle(), "Java форум JTalks");
	}

}
