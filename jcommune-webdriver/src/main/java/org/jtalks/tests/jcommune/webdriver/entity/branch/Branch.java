package org.jtalks.tests.jcommune.webdriver.entity.branch;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author stanislav bashkirtsev
 * @author andrey ivanov
 */
public class Branch {
    private int id;
    private String title;

    public Branch(String title) {
        this.title = title;
    }

    public static List<Branch> fromForm(List<WebElement> webElements) {
        List<Branch> branches = new ArrayList<>(webElements.size());
        for (WebElement element : webElements) {
            branches.add(fromForm(element));
        }
        return branches;
    }

    public static Branch fromForm(WebElement webElement) {
        Branch branch = new Branch(webElement.getText());
        String href = webElement.getAttribute("href");
        return branch.withId(parseBranchIdFromHref(href));
    }

    /**
     * Getting id from branch href.
     *
     * @param href something like {@code /branches/123}
     * @return branch id cut from branch href
     */
    private static int parseBranchIdFromHref(String href) {
        href = StringUtils.removeEnd(href, "/");
        Pattern pattern = Pattern.compile("[0-9]+$");
        Matcher matcher = pattern.matcher(href);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        throw new IllegalArgumentException("Branch href couldn't be recognized to parse the ID from it: " + href);
    }

    public String getTitle() {
        return title;
    }

    public Branch withTitle(String title) {
        this.title = title;
        return this;
    }

    public int getId() {
        return id;
    }

    public Branch withId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Branch: id=[%s], title=[%s]", this.id, this.title);
    }
}
