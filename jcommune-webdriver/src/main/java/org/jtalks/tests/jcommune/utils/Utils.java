package org.jtalks.tests.jcommune.utils;

import org.openqa.selenium.Keys;

import java.awt.datatransfer.StringSelection;

import static java.awt.Toolkit.getDefaultToolkit;
import static org.openqa.selenium.Keys.chord;

/**
 * @author baranov.r.p
 */
public class Utils {
    public static final String PASTE_CHORD = chord(Keys.CONTROL + "v");
    public static final String OPEN_NEW_TAB_CHORD = chord(Keys.CONTROL + "t");
    public static final String CLOSE_CURRENT_TAB_CHORD = chord(Keys.CONTROL + "w");

    public static void setTextToClipboard(String text) {
        getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
    }
}