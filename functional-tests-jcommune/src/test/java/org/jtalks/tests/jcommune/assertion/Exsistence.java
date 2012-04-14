package org.jtalks.tests.jcommune.assertion;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.fail;

/**
 * The class contains a check for the existence of elements
 *
 * @author masyan
 */
public class Exsistence {

	/**
	 * The method checks that the element does not exist
	 *
	 * @param selector Selector to find element
	 */
	public static void assertionNotExistBySelector(WebDriver driver, String selector) {
		try {
			driver.findElement(By.xpath(selector));
			//if exist then fail
			fail("The element with selector=\"" + selector + "\" should not exist");
		}
		catch (NoSuchElementException e) {
		}
	}

	/**
	 * The method checks that the element does exist
	 *
	 * @param selector Selector to find element
	 */
	public static void assertionExistBySelector(WebDriver driver, String selector) {
		try {
			driver.findElement(By.xpath(selector));
		}
		catch (NoSuchElementException e) {
			//if not exist then fail
			fail("The element with selector=\"" + selector + "\" should exist");
		}
	}

	/**
	 * The method checks that the element does not exist
	 *
	 * @param id Element id
	 */
	public static void assertionNotExistById(WebDriver driver, String id) {
		try {
			driver.findElement(By.id(id));
			//if exist then fail
			fail("The element with id=\"" + id + "\" should not exist");
		}
		catch (NoSuchElementException e) {
		}
	}

	/**
	 * The method checks that the element does exist
	 *
	 * @param id Element id
	 */
	public static void assertionExistById(WebDriver driver, String id) {
		try {
			driver.findElement(By.id(id));
		}
		catch (NoSuchElementException e) {
			//if exist then fail
			fail("The element with id=\"" + id + "\" should exist");
		}
	}

	/**
	 * The method  checks elements in collection. If collection is Empty, then generate exception
	 *
	 * @param webElementsList collection which should contains elements
	 */
	public static void assertionNotEmptyCollection(List<WebElement> webElementsList) {
		if (webElementsList.size() == 0) {
			fail("Elements collection is empty");
		}
	}

	/**
	 * The method  checks elements in collection. If collection is NOT Empty, then generate exception
	 *
	 * @param webElementsList collection which should not contains elements
	 */
	public static void assertionEmptyCollection(List<WebElement> webElementsList) {
		if (webElementsList.size() != 0) {
			fail("Collection is not empty");
		}
	}

	/**
	 * @param str  String in which the pattern is searched
	 * @param find Searched pattern
	 */
	public static void assertionContainsInString(String str, String find) {
		if (!str.contains(find)) {
			fail("String does not contain the search pattern");
		}
	}

	/**
	 * @param str  String in which the pattern is searched
	 * @param find Searched pattern
	 */
	public static void assertionNotContainsInString(String str, String find) {
		if (str.contains(find)) {
			fail("String contains the search pattern");
		}
	}

	/**
	 * this method return true when in list presents the desired text
	 *
	 * @param list The list of webelements
	 * @param text the desired text
	 * @return
	 */
	public static void assertionNotExistElementOnViewPresent(List<WebElement> list, String text) {
		for (WebElement webElement : list) {
			String t = webElement.getText();
			if (t.equals(text)) {
				fail("WebElement with text '" + text + "' exist on view");
			}
		}
	}

	/**
	 * this method return true when in list presents  exist this text
	 *
	 * @param list The list of webelements
	 * @param text the desired text
	 * @return
	 */
	public static void assertionExistElementOnViewPresent(List<WebElement> list, String text) {
		boolean exist = false;
		for (WebElement webElement : list) {
			String t = webElement.getText();
			if (t.equals(text)) {
				exist = true;
				return;
			}
		}
		if (!exist) {
			fail("WebElement with text '" + text + "' not exist on view");
		}
	}

}
