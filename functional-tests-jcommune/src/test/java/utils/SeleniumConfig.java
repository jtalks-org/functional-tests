package utils;

import org.apache.commons.lang.ArrayUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class contains parametrs of the remote Selenium server.
 *
 * @author masyan
 */
public class SeleniumConfig {
	/**
	 * Array contains browser types.
	 */
	private static String[] browsers = {"chrome", "firefox", "ie", "opera", "android", "ipad", "iphone", "html"};

	/**
	 * @param browser Name  of browser (or short name). Values: chrome, ie, opera, android, ipad, iphone, html.
	 * @return Returns driver  for create RemoteWebDriver (Selenium)
	 */
	public static DesiredCapabilities getBrowserDriver(String browser) {

		byte browserInd = (byte) ArrayUtils.indexOf(browsers, browser);

		switch (browserInd) {
			case 0:
				return DesiredCapabilities.chrome();
			case 1:
				return DesiredCapabilities.firefox();
			case 2:
				return DesiredCapabilities.internetExplorer();
			case 3:
				return DesiredCapabilities.opera();
			case 4:
				return DesiredCapabilities.android();
			case 5:
				return DesiredCapabilities.ipad();
			case 6:
				return DesiredCapabilities.iphone();
			case 7:
				return DesiredCapabilities.htmlUnit();
			default:
				return DesiredCapabilities.htmlUnit();
		}
	}
}
