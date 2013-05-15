package org.jtalks.tests.jcommune.webdriver.topic;

/**
 * Jcommune poll item representation
 */
public class PollItem {

    private String name;
    private int votesCount;
    private Poll poll;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(int votesCount) {
        this.votesCount = votesCount;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
