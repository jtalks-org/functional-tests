package utils;

import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Utils class. Contains methods to work with Collections
 *
 * @autor masyan
 */
public class CollectionHelp {

	/**
	 * Method returns one randomize WebElement from collection
	 *
	 * @param elements it is  collection contains WebElements
	 * @return one randomize element from collection
	 */
	public static WebElement getRandomWebElementFromCollection(List<WebElement> elements) {
		if (elements.size() == 0) {
			return null;
		}
		Random random = new Random((new Date()).getTime());
		int idx = random.nextInt(elements.size());
		return elements.get(idx);
	}

	/**
	 * Method returns last WebElement from collection
	 *
	 * @param elements it is  collection contains WebElements
	 * @return last element from collection
	 */
	public static WebElement getLastWebElementFromCollection(List<WebElement> elements) {
		if (elements.size() == 0) {
			return null;
		}
		return elements.get(elements.size() - 1);
	}

	/**
	 * Method returns first WebElement from collection
	 *
	 * @param elements it is  collection contains WebElements
	 * @return first element from collection
	 */
	public static WebElement getFirstWebElementFromCollection(List<WebElement> elements) {
		if (elements.size() == 0) {
			return null;
		}
		return elements.get(0);
	}

}
