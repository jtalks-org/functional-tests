package org.jtalks.tests.jcommune.webdriver.entity.topic;

import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Jcommune code review representation.
 */
public class CodeReview extends Topic {

    private String content;
    private int linesNumber = 1;
    private List<CodeReviewComment> comments;

    public CodeReview() {
        this.withTitle(randomAlphanumeric(30));
        this.content = randomAlphanumeric(200);
    }

    public CodeReview(String title, String topicContent) {
        this.withTitle(title);
        this.content = topicContent;
    }

    public CodeReview withContent(String content) {
        this.content = content;
        return this;
    }

    public CodeReview setTitle(String title){
        this.withTitle(title);
        return this;
    }

    public CodeReview setBranch(Branch branch) {
        this.withBranch(branch);
        return this;
    }

    public CodeReview setBranch(String branchTitle) {
        this.withBranch(branchTitle);
        return this;
    }

    public CodeReview withLinesNumber(int linesNumber) {
        this.linesNumber = linesNumber;
        return this;
    }

    public String getContent() {
        return content;
    }

    public int getLinesCount() {
        return linesNumber;
    }

    public List<CodeReviewComment> getComments() {
        return comments;
    }

    public void setComments(List<CodeReviewComment> comments) {
        this.comments = comments;
    }

    public void addComment(CodeReviewComment codeReviewComment) {
        this.comments.add(codeReviewComment);
    }

    @Override
    public String toString() {
        return String.format("Code review: title=[%s]", this.getTitle());
    }

}
