package org.jtalks.tests.jcommune.webdriver.entity.topic;

import org.joda.time.DateTime;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Jcommune code review representation.
 */
public class CodeReview {

    private Branch branch;
    private DateTime creationDate;
    private DateTime modificationDate;
    private User topicStarter;
    private String title = randomAlphanumeric(30);
    private String topicContent = randomAlphanumeric(50);
    private int linesNumber = 1;
    private Boolean closed;
    private int views;
    private boolean hasNewMessages;
    private List<CodeReviewComment> comments = new ArrayList<CodeReviewComment>();

    public CodeReview() {}

    public CodeReview(String title, String topicContent) {
        this.title = title;
        this.topicContent = topicContent;
    }

    public CodeReview withBranch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public CodeReview withBranch(String branchTitle) {
        this.branch = new Branch(branchTitle);
        return this;
    }

    public CodeReview withTopicStarter(User topicStarter) {
        this.topicStarter = topicStarter;
        return this;
    }

    public CodeReview withTitle(String title){
        this.title = title;
        return this;
    }

    public CodeReview withBody(String topicContent) {
        this.topicContent = topicContent;
        return this;
    }

    public CodeReview withLinesNumber(int linesNumber) {
        this.linesNumber = linesNumber;
        return this;
    }

    public DateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(DateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public User getTopicStarter() {
        return topicStarter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Branch getBranch() {
        return branch;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public int getLinesNumber() {
        return linesNumber;
    }

    public boolean hasNewMessages() {
        return hasNewMessages;
    }

    public void setHasNewMessages(boolean hasNewMessages) {
        this.hasNewMessages = hasNewMessages;
    }

    public List<CodeReviewComment> getComments() {
        return comments;
    }

    public void setComments(List<CodeReviewComment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return String.format("Code review: title=[%s]", this.title);
    }

}
