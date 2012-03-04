package org.jtalks.tests.jcommune.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: erik
 * Date: 04.03.12
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class TopicCreationPage {
    public static final String subjectFieldId = "subject";
    public static final String messageFieldId = "tbMsg";
    public static final String postButtonId = "post";
    public static final String subjectErrorMessageSel = "//span[@class='error' and @id='subject']";
    public static final String bodyErrorMessageSel = "//span[@class='error' and @id='bodyText.errors']";





    @FindBy(id = subjectFieldId)
    WebElement subjectField;

    @FindBy(id = messageFieldId)
    WebElement messageField;

    @FindBy(id = postButtonId)
    WebElement postButton;

    @FindBy(xpath = subjectErrorMessageSel)
    WebElement subjectErrorMessage;

    @FindBy(xpath = bodyErrorMessageSel)
    WebElement bodyErrorMessage;


    public TopicCreationPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }


    //Getters
    public WebElement getSubjectField() {
        return subjectField;
    }

    public WebElement getMessageField() {
        return messageField;
    }

    public WebElement getPostButton() {
        return postButton;
    }

    public WebElement getSubjectErrorMessage() {
        return subjectErrorMessage;
    }

    public WebElement getBodyErrorMessage() {
        return bodyErrorMessage;
    }
}
