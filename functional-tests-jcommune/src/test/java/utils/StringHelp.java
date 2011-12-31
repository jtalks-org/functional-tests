package utils;

import java.util.Date;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: masyan
 * Date: 31.12.11
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public class StringHelp {

	/**
	 * this method generates String of random chars necessary length
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		Random random = new Random((new Date()).getTime());
		char[] values = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
				'4', '5', '6', '7', '8', '9'};
		String out = "";
		for (int i = 0; i < length; i++) {
			int idx = random.nextInt(values.length);
			out += values[idx];
		}
		return out;
	}
}
