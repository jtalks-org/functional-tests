package org.jtalks.tests.jcommune.utils;

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

    /**
     * Method returns first WebElement from collection by index
     *
     * @param elements it is  collection contains WebElements
     * @param index    of element. Index start by 1
     * @return first element from collection
     */
    public static WebElement getWebElementFromCollectionByIndex(List<WebElement> elements, int index) {
        if (elements.size() < index) {
            return null;
        }
        return elements.get(index - 1);
    }

    /**
     * Method  find text in collection
     *
     * @param elements Collection to find
     * @param text     Value which will fin in collection
     * @return true if  text found in collection, else false
     */
    public static boolean containsTextInCollection(List<WebElement> elements, String text) {
        boolean result = false;
        for (WebElement element : elements) {
            if (element.getText().equals(text)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Method  find element by value in collection
     *
     * @param elements Collection to find
     * @param value    Value to find
     * @return element if was found and return null else
     */
    public static WebElement findWebElementByValueInCollection(List<WebElement> elements, String value) {
        WebElement result = null;
        for (WebElement element : elements) {
            if (element.getText().equals(value)) {
                result = element;
                break;
            }
        }
        return result;
    }

}
