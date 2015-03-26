package org.jtalks.tests.jcommune.webdriver.entity.topic;

/**
 * Jcommune code review comment representation.
 */
public class CodeReviewComment extends Post {

    private int commentedLineNumber = 1;
    private CodeReview codeReview;

    public int getCommentedLineNumber() {
        return commentedLineNumber;
    }

    public CodeReviewComment onLineNumber(int commentedLineNumber) {
        this.commentedLineNumber = commentedLineNumber;
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
