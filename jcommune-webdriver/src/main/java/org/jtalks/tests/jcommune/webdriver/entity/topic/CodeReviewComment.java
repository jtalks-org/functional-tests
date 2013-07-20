package org.jtalks.tests.jcommune.webdriver.entity.topic;

import org.joda.time.DateTime;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

/**
 * Jcommune code review comment representation.
 */
public class CodeReviewComment {

    private int lineNumber;
    private User author;
    private DateTime creationDate;
    private String body;
    private CodeReview codeReview;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CodeReview getCodeReview() {
        return codeReview;
    }

    public void setCodeReview(CodeReview codeReview) {
        this.codeReview = codeReview;
    }
}
