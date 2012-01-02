package org.jtalks.tests.jcommune.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.DBHelp;
import utils.SeleniumConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Parent class for Tests. Contains common methods
 *
 * @author masyan
 */
public class JCommuneSeleniumTest {
	/**
	 * Object for work with Remote Selenium Server
	 */
	public WebDriver driver;

	public Connection dbConnection;

	/**
	 * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server.
	 * All values are stored in tesng.xml file.
	 *
	 * @param selServerURL  Selenium server URL
	 * @param selDriverType Selenium driver type
	 * @throws MalformedURLException
	 */
	@BeforeClass(alwaysRun = true)
	@Parameters({"selenium-server-url", "selenium-driver-type", "db-url", "db-driver", "db-username", "db-password"})
	public void init(String selServerURL, String selDriverType, String dbURL, String dbDriver, String username, String password) throws Exception {
		driver = new RemoteWebDriver(
				new URL(selServerURL),
				SeleniumConfig.getBrowserDriver(selDriverType));
		dbConnection = DBHelp.getConnection(dbURL, dbDriver, username, password);
		DBHelp.setForumUsers(dbConnection);
	}

	/**
	 * Method destroy connect with Selenium Server
	 */
	@AfterClass(alwaysRun = true)
	public void destroy() {
		driver.close();
		if (dbConnection != null) {
			try {
				dbConnection.close();
			}
			catch (SQLException e) {

			}
		}
	}


	/**
	 * This method does the authentication
	 *
	 * @param username
	 * @param password
	 * @author eric
	 */
	public void signIn(String username, String password, String appURL) {
		driver.findElement(By.xpath("//a[@href='/jcommune/login']")).click();
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	}
}
