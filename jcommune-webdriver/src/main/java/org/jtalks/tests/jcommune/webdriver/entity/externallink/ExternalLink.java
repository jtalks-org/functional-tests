package org.jtalks.tests.jcommune.webdriver.entity.externallink;

import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.utils.StringHelp;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

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

    public static ExternalLink fromForm(WebElement externalLinkFromForm) {
        String actualHref = externalLinkFromForm.getAttribute("href").replaceAll("/$", "");//browser may add /
        String actualTitle = DriverMethodHelp.getTextFromTag(externalLinkFromForm).trim();
        String actualHint = externalLinkFromForm.getAttribute("data-original-title");
        return new ExternalLink(actualTitle).withHint(actualHint).withHref(actualHref);
    }

    public static List<ExternalLink> fromForm(List<WebElement> externalLinksFromForm) {
        List<ExternalLink> toReturn = new ArrayList<>();
        for (WebElement link : externalLinksFromForm) {
            toReturn.add(ExternalLink.fromForm(link));
        }
        return toReturn;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalLink that = (ExternalLink) o;

        if (hint != null ? !hint.equals(that.hint) : that.hint != null) return false;
        if (href != null ? !href.equalsIgnoreCase(that.href) : that.href != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (href != null ? href.hashCode() : 0);
        result = 31 * result + (hint != null ? hint.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExternalLink{" +
                "title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", hint='" + hint + '\'' +
                '}';
    }
}
