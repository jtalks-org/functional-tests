package org.jtalks.tests.jcommune.webdriver.entity.topic;

import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Jcommune code review representation.
 */
public class CodeReview extends Topic {

    private String content;
    private int numberOfLines;
    private List<CodeReviewComment> comments;

    public CodeReview() {
        this.numberOfLines = (int)(Math.random()*10 + 1);
        this.withTitle(randomAlphanumeric(30));
        for (int i = 0; i < this.numberOfLines; i++){
            this.content += randomAlphanumeric(10) + System.getProperty("line.separator");
        }
    }

    public CodeReview(String title, String content) {
        this.withTitle(title);
        this.content = content;
        this.numberOfLines = 1;
    }

    public CodeReview withContent(String content) {
        this.content = content;
        this.numberOfLines = 1;
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

    // New content would be generated for each line of a code review
    public CodeReview withNumberOfLines_RandContent(int numberOfLines) {
        this.numberOfLines = numberOfLines;
        this.content = "";
        for (int i = 0; i < this.numberOfLines; i++){
            this.content += randomAlphanumeric(10) + System.getProperty("line.separator");
        }
        return this;
    }

    // Passed argument %content% would be repeated for each line of a code review
    public CodeReview withNumberOfLines_SetContent(int numberOfLines, String content) {
        this.numberOfLines = numberOfLines;
        this.content = "";
        for (int i = 0; i < this.numberOfLines; i++){
            this.content += content + System.getProperty("line.separator");
        }
        return this;
    }

    public String getContent() {
        return content;
    }

    public int getLinesCount() {
        return numberOfLines;
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
