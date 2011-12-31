package org.jtalks.tests.jcommune.rserver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.SeleniumConfig;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: masyan
 * Date: 31.12.11
 * Time: 22:47
 * To change this template use File | Settings | File Templates.
 */
public class SeleniumServer {

	public WebDriver driver;

	@BeforeClass(alwaysRun = true)
	@Parameters({"selenium-server-url", "selenium-driver-type"})
	public void init(String selServerURL, String selDriverType) throws Exception {
		driver = new RemoteWebDriver(
				new URL(selServerURL),
				SeleniumConfig.getBrowserDriver(selDriverType));
	}

	@AfterClass(alwaysRun = true)
	public void destroy() {
		driver.close();
	}

}
