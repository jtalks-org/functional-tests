package utils;

import java.util.Date;
import java.util.Random;

/**
 * Utols class. Contains methods to work with String
 *
 * @author masyan
 */
public class StringHelp {

	private static String testPrefix = "P_";

	/**
	 * this method generates String of random chars necessary length
	 *
	 * @param length
	 * @return String  contains random characters (count=length)
	 */
	public static String getRandomString(int length) {
		Random random = new Random((new Date()).getTime());
		char[] values = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
				'4', '5', '6', '7', '8', '9'};
		String out = "";

		if (length == testPrefix.length()) {
			return testPrefix;
		}
		else if (length < testPrefix.length()) {
			for (int i = 0; i < length; i++) {
				int idx = random.nextInt(values.length);
				out += values[idx];
			}
		}
		else {
			out += testPrefix;
			for (int i = 0; i < length - testPrefix.length(); i++) {
				int idx = random.nextInt(values.length);
				out += values[idx];
			}
		}


		return out;
	}
}
