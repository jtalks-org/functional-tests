package org.jtalks.tests.jcommune.webdriver.entity.topic;

/**
 * Jcommune code review comment representation.
 */
public class CodeReviewComment extends Post {

    private int lineNumber = 1;
    private CodeReview codeReview;

    public int getLineNumber() {
        return lineNumber;
    }

    public CodeReviewComment withLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public CodeReviewComment withContent(String content) {
        this.setPostContent(content);
        return this;
    }

    public CodeReview getCodeReview() {
        return codeReview;
    }

    public void setCodeReview(CodeReview codeReview) {
        this.codeReview = codeReview;
    }
}
