package org.jtalks.tests.jcommune.webdriver.entity.externallink;

import org.jtalks.tests.jcommune.utils.StringHelp;

/**
 * @author stanislav bashkirtsev
 */
public class ExternalLink {
    private String title = StringHelp.randomString(30);
    private String href = StringHelp.randomUrl(30);
    private String hint = StringHelp.randomString(30);

    public ExternalLink() {
    }

    public ExternalLink(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ExternalLink withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getHref() {
        return href;
    }

    public ExternalLink withHref(String href) {
        this.href = href;
        return this;
    }

    public String getHint() {
        return hint;
    }

    public ExternalLink withHint(String hint) {
        this.hint = hint;
        return this;
    }


}
