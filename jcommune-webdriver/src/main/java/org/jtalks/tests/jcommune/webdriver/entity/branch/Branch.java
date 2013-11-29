package org.jtalks.tests.jcommune.webdriver.entity.branch;

/**
 * @author stanislav bashkirtsev
 * @author andrey ivanov
 */
public class Branch {
    private long id;
    private String title;

    public Branch() {
    }

    public Branch(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Branch withTitle(String title) {
        this.title = title;
        return this;
    }

    public long getId() {
        return this.id;
    }

    public Branch withId(long id) {
        this.id = id;
        return this;
    }
}
