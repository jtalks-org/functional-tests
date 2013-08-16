package org.jtalks.tests.jcommune.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils class. Contains methods to work with String
 *
 * @author masyan
 */
public class StringHelp {

    private static String testPrefix = "P_";

    static char[] values = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9'};

    /**
     * this method generates String of random chars necessary length
     *
     * @return String  contains random characters (count=length)
     */
    public static String randomString(int length) {
        Random random = new Random((new Date()).getTime());
        String out = "";

        if (length == testPrefix.length()) {
            return testPrefix;
        } else if (length < testPrefix.length()) {
            for (int i = 0; i < length; i++) {
                int idx = random.nextInt(values.length);
                out += values[idx];
            }
        } else {
            out += testPrefix;
            for (int i = 0; i < length - testPrefix.length(); i++) {
                int idx = random.nextInt(values.length);
                out += values[idx];
            }
        }
        return out;
    }

    /**
     * this method generates String of random chars necessary length
     *
     * @return String  contains random characters (count=length)
     */
    public static String randomUrl(int length) {
        int protocol = 7;
        if(length <= protocol){
            return randomString(length);
        }else{
            return "http://" + randomString(length - protocol);
        }
    }

    /**
     * Method  generate random eMail address.
     *
     * @return Streing with eMail address
     */
    public static String getRandomEmail() {
        String eMail = "";
        eMail += randomString(12) + "@jtalks.org";
        return eMail;
    }

    /**
     * Method set value to WebElement. This method should be used if value very long.
     *
     * @param driver  WebDriver
     * @param element Element in which you can enter
     * @param value   The entered value
     */
    public static void setLongTextValue(WebDriver driver, WebElement element, String value) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]", element, value);
    }

    /**
     * Method return String contains test prefix.
     *
     * @return String contains test prefix
     */
    public static String getTestPrefix() {
        return testPrefix;
    }

    /**
     * Method check text and find updtaed. All test users should have English locale. In method used regexp which not
     * work
     * with another locale
     *
     * @param post Text of post
     * @return true if update exist, else false
     */
    public static boolean checkUpdatedPost(String post) {
        Pattern p = Pattern.compile("[\\w\\s\\t]*(edited\\s\\d{2}\\s[a-zA-Z]{3}\\s\\d{4}\\s\\d{2}:\\d{2})$");
        Matcher m = p.matcher(post);
        return m.matches();
    }

    /**
     * Method return url without host
     *
     * @param fullUrl     Full url
     * @param contextPath Context path it is first part after host
     * @return Url without
     */
    public static String getUrlWithoutHost(String fullUrl, String contextPath) {
        String[] parts = fullUrl.split(contextPath);
        if (parts.length > 0) {
            return contextPath + parts[parts.length - 1];
        } else {
            return null;
        }
    }
}
