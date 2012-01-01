package org.jtalks.tests.jcommune.common;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import utils.DBUnitConfig;
import utils.SeleniumConfig;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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

	/**
	 * Method  execute before execute Test. This method getting  driver for connect Remote Selenium Server.
	 * All values are stored in tesng.xml file.
	 *
	 * @param selServerURL  Selenium server URL
	 * @param selDriverType Selenium driver type
	 * @param dbURL		 Database URL for  connection to DB
	 * @param dbDriver	  String containt class name for database driver.
	 * @param username	  Username to REMOTE connect to database
	 * @param password	  Passwort for username
	 * @throws MalformedURLException
	 */
	@BeforeClass(alwaysRun = true)
	@Parameters({"selenium-server-url", "selenium-driver-type", "db-url", "db-driver", "db-username", "db-password", "db-type"})
	public void init(String selServerURL, String selDriverType, String dbURL, String dbDriver, String username, String password, String dbType) throws Exception {
		driver = new RemoteWebDriver(
				new URL(selServerURL),
				SeleniumConfig.getBrowserDriver(selDriverType));


		IDatabaseConnection conn = this.getConnection(dbDriver, dbURL, username, password);
		DatabaseConfig config = conn.getConfig();
		config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				DBUnitConfig.getDataTypeFactory(dbType));

		IDataSet users = this.getDataSetUsers();

		try {
			DatabaseOperation.DELETE.execute(conn, users);
			DatabaseOperation.INSERT.execute(conn, users);
		}
		catch (SQLException e) {

		}
		catch (DatabaseUnitException e) {

		} finally {
			try {
				conn.close();
			}
			catch (SQLException e) {

			}
		}
	}

	/**
	 * Method destroy connect with Selenium Server
	 */
	@AfterClass(alwaysRun = true)
	public void destroy() {
		driver.close();
	}

	/**
	 * Method returns Database connection.
	 *
	 * @param driver   Driver class name
	 * @param url	  Database URL
	 * @param user	 Database user with Remote connect permissions
	 * @param password Password for username
	 * @return DatabaseConnection object for connect to database
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DatabaseUnitException
	 */
	private IDatabaseConnection getConnection(String driver, String url, String user, String password)
			throws ClassNotFoundException, SQLException, DatabaseUnitException {
		Class.forName(driver);
		Connection jdbcConnection = DriverManager.getConnection(url, user, password);
		return new DatabaseConnection(jdbcConnection);
	}

	/**
	 * Method return data users for insert. Data is stored in dbu_users.xlm file
	 *
	 * @return XmlDataSet object with data from dbu_users.xml file
	 * @throws Exception
	 */
	protected IDataSet getDataSetUsers() throws Exception {
		File dir = new File("");
		String fSep = System.getProperty("file.separator");
		String fileDataPath = dir.getAbsolutePath() + fSep + "functional-tests" + fSep + "functional-tests-jcommune" +
				fSep + "src" + fSep + "test" + fSep + "data" + fSep;
		return new XmlDataSet(new FileInputStream(fileDataPath + "dbu_users.xml"));
	}

	/**
	 * This method does the authentication
	 *
	 * @param username
	 * @param password
	 * @author eric
	 */
	public void signIn(String username, String password, String appURL) {
		driver.get(appURL + "/login");
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	}
}
