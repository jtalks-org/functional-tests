package config;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Singleton. Class contains parametrs of the remote Selenium server.
 *
 * @author masyan
 */
public class SeleniumConfig {
	private static SeleniumConfig ourInstance = new SeleniumConfig();
	/**
	 * URL  of the server
	 */
	private static String server = "http://autotest.jtalks.org:4444/wd/hub";
	/**
	 * Values:
	 * 1 - Chrome
	 * 2 - Firefox
	 * 3 - IE
	 * 4 - Opera
	 * 5 - Android
	 * 6 - iPad
	 * 7 - iPhone
	 * another values - htmlUnit driver (test without browser) (for examle  8)
	 */
	private static byte browser = 8;

	/**
	 * @return URL of the remote Selenium server. Needed forcreate RemoteWebDriver  for tests.
	 */
	public static String getRemoteSeleniumServerUrl() {
		return server;
	}

	;

	/**
	 * @return Returns driver  for create RemoteWebDriver (Selenium)
	 */
	public static DesiredCapabilities getBrowserDriver() {
		switch (browser) {
			case 1:
				return DesiredCapabilities.chrome();
			case 2:
				return DesiredCapabilities.firefox();
			case 3:
				return DesiredCapabilities.internetExplorer();
			case 4:
				return DesiredCapabilities.opera();
			case 5:
				return DesiredCapabilities.android();
			case 6:
				return DesiredCapabilities.ipad();
			case 7:
				return DesiredCapabilities.iphone();
			default:
				return DesiredCapabilities.htmlUnit();
		}
	}


	public static SeleniumConfig getInstance() {
		return ourInstance;
	}

	private SeleniumConfig() {
	}
}
