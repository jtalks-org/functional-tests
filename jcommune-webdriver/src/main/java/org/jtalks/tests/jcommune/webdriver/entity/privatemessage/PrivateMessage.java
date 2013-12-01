package org.jtalks.tests.jcommune.webdriver.entity.privatemessage;

import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.joda.time.DateTime;

/**
 * JCommune private messages representation
 *
 * @author Varro
 */
public class PrivateMessage {
    private User author;
    private User receiver;
    private String messageTopic;
    private String messageContent;
    private DateTime creationDate;
    private boolean read;

    public PrivateMessage(User receiver, String messageTopic, String postContent) {
        this.receiver = receiver;
        this.messageTopic = messageTopic;
        this.messageContent = postContent;
    }

    public PrivateMessage(User author, User receiver, String messageTopic, String postContent) {
        this.author = author;
        this.receiver = receiver;
        this.messageTopic = messageTopic;
        this.messageContent = postContent;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getReceiver() {
        return this.receiver;
    }

    public void setReveiver(User receiver){
        this.receiver = receiver;
    }

    public String getMessageTopic() {
        return this.messageTopic;
    }

    public void setMessageTopic(String messageTopic){
        this.messageTopic = messageTopic;
    }

    public String getMessageContent(){
        return this.messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
