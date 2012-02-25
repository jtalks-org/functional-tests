package org.jtalks.tests.jcommune.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.fail;

/**
 * The class contains a check for the existence of elements
 *
 * @autor masyan
 */
public class Exsistence {

	/**
	 * The method checks that the element does not exist
	 *
	 * @param selector Selector to find element
	 */
	public static void assertNotExistBySelector(WebDriver driver, String selector) {
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
	public static void assertExistBySelector(WebDriver driver, String selector) {
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
	public static void assertNotExistById(WebDriver driver, String id) {
		try {
			driver.findElement(By.id(id));
			//if exist then fail
			fail("The element with id=\"" + id + "\" should not exist");
		}
		catch (NoSuchElementException e) {
		}
	}

	/**
	 * The method checks that the element does not exist
	 *
	 * @param id Element id
	 */
	public static void assertExistById(WebDriver driver, String id) {
		try {
			driver.findElement(By.id(id));
		}
		catch (NoSuchElementException e) {
			//if exist then fail
			fail("The element with id=\"" + id + "\" should exist");
		}
	}

}
