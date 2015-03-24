package org.jtalks.tests.jcommune.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utils class. Contains methods to work with String
 *
 * @author masyan
 */
public class TestStringUtils {
    /**
     * Generates String of random latin and numeric symbols of specified length.
     *
     * @return string of specified size that contains random characters
     * @deprecated use {@link RandomStringUtils}
     */
    @Deprecated
    public static String randomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * this method generates String of random chars necessary length
     *
     * @return String  contains random characters (count=length)
     */
    public static String randomUrl(int length) {
        int protocol = 7;
        if (length <= protocol) {
            return RandomStringUtils.randomAlphanumeric(length);
        } else {
            return "http://" + RandomStringUtils.randomAlphanumeric(length - protocol);
        }
    }
}
