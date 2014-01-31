/**
 * Created by Gradobik on 31.01.14.
 * заготовка для тестов по бб-коду
 */
package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Poll;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
//text attributes
import java.swing.JTextPane;
import java.awt.Font;
import java.swing.text.SimpleAttributeSet;

class bb_codes {
    // set a bb-code method
    public  setBBCode() {
        //set a string type for a future bb-codes, possible could be an array like {"b","/b"}
        //String bb_code() = {
        //    return bb_code;
        //};
        String[][] bb_code() = {
            {"b","/b"}, {"i","/i"}
            retunt bb_code; 
            /*возможно бб коды описать массивами и вызывать по типу bb_code[0][1] который и будет
            описывать открывающий или закрывающий тэг*/
        };
         
    }
    
    /*method for text comparison
    public setText(String[], args) {
        JTextPane textPane = new JTextPane();
        textPane.setText("message"); // add text "message"
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setBold(sas, true);//text Bold 
        textPane.
        
        } */
    
    
    

    @Test(enabled = true)
    public void usingAbb_codeForASsingleExpressionInNewTopiсScreen_QA_7() {
        Users.signUpAndSignIn();
        /*setBBCode bb_code1 = new bb_codes();
        setBBCode bb_code2 = new bb_codes();
        bb_code1.setBBCode = "b";
        bb_code2.setBBCode = "/b";*/
        Topic topic = new Topic("subject", bb_code[0][0] + "message" + bb_code[0][1]);
        Topic createdTopic = Topics.createTopic(topic);
    }
}
