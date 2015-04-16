package org.jtalks.tests.jcommune.webdriver.entity.topic;

import com.google.common.base.Splitter;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Jcommune code review representation.
 */
public class CodeReview extends Topic {

    private String content = randomAlphanumeric(50);
    private int numberOfLines = 1;
    private List<CodeReviewComment> comments;

    public CodeReview() {}

    public CodeReview(String title, String content) {
        this.withTitle(title);
        this.content = content;
    }

    public CodeReview withContent(String content) {
        this.content = "";
        if (this.numberOfLines > 1) {
            for (String token : Splitter.fixedLength(content.length()/this.numberOfLines).split(content)){
                this.content += token + System.getProperty("line.separator");
            }
        } else {
            this.content = content;
        }
        return this;
    }

    public CodeReview withNumberOfLines(int numberOfLines) {
        this.numberOfLines = numberOfLines;
        if (!this.content.equals("")) {
            withContent(this.content);
        } else {
            for (int i = 0; i < this.numberOfLines; i++){
                this.content += randomAlphanumeric(10) + System.getProperty("line.separator");
            }
        }
        return this;
    }

    public CodeReview withTitle(String title){
        super.withTitle(title);
        return this;
    }

    public CodeReview withBranch(String branchName) {
        throw new UnsupportedOperationException("To be implemented");
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
