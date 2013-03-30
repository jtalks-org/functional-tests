package org.jtalks.tests.jcommune.mail;

import org.jtalks.tests.jcommune.mail.exim.EximMailImpl;
import org.apache.commons.lang.ArrayUtils;

/**
 * Class contains parametrs of the mail server
 *
 * @autor masyan
 */
public class MailHelp {
	/**
	 * Array contains mail servers types.
	 */
	private static String[] servers = {"exim"};

	/**
	 * @param mailType Short name to used mail server
	 * @return Mail implementation instance
	 */

	public static Mail getMailImpl(String mailType) {

		byte typeInd = (byte) ArrayUtils.indexOf(servers, mailType);

		switch (typeInd) {
			case 0:
				return new EximMailImpl();
			default:
				return new EximMailImpl();
		}
	}
}
