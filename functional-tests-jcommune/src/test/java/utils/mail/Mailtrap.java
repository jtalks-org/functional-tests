/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package utils.mail;

import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Guram Savinov
 */
public class Mailtrap {

    private static final String MAILTRAP_SIGNIN_URL = "http://mailtrap.io/auth/sessions/new";
    private static final String MAILTRAP_EMAIL = "project@jtalks.org";
    private static final String MAILTRAP_PASSWORD = "do7iePei";
    private static final String MAILTRAP_MESSAGES_URL = "http://mailtrap.io/inboxes/jtalks-autotests/messages/";

    private WebDriver driver;
    private List<Metadata> metadataList;

    public Mailtrap(WebDriver driver) {
        this.driver = driver;
    }

    public void signIn() {
        driver.get(MAILTRAP_SIGNIN_URL);
        driver.findElement(By.id("user_session_email")).sendKeys(MAILTRAP_EMAIL);
        driver.findElement(By.id("user_session_password")).sendKeys(MAILTRAP_PASSWORD);
        driver.findElement(By.name("button")).click();

        driver.get(MAILTRAP_MESSAGES_URL);
        metadataList = parseMetadata(driver.getPageSource());
    }

    public String getActivationLink(String recipient) {
        String id = "0";
        String link = null;
        for (Metadata metadata : metadataList) {
            if (!recipient.equals(metadata.getRecipient())) {
                continue;
            }
            if (Long.parseLong(id) < Long.parseLong(metadata.getId())) {
                id = metadata.getId();
            }
        }
        if (id.equals("0")) {
            return link;
        }

        driver.get(MAILTRAP_MESSAGES_URL + id + "/body?view=RAW_VIEW");
        try {
            MimeMessage message = new MimeMessage(Session.getInstance(new Properties()),
                    (new ByteArrayInputStream(driver.getPageSource().getBytes())));
            Multipart multipart = (Multipart) message.getContent();
            Multipart multipart1 = (Multipart) multipart.getBodyPart(0).getContent();
            Multipart multipart2 = (Multipart) multipart1.getBodyPart(0).getContent();
            String escapedText = (String) multipart2.getBodyPart(0).getContent();
            String text = StringEscapeUtils.unescapeHtml(escapedText);
            Matcher matcher = Pattern.compile("(http://.*/activate/.*)[\\s]").matcher(text);
            if(matcher.find()){
                link = matcher.group(1);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return link;
    }

    private List<Metadata> parseMetadata(String source) {
        List<Metadata> metadataList = new ArrayList<Metadata>();
        Matcher matcher = Pattern.compile("\"id\":(\\d*)").matcher(source);
        while (matcher.find()) {
            Metadata metadata = new Metadata();
            metadata.setId(matcher.group(1));
            metadataList.add(metadata);
        }
        matcher = Pattern.compile("created_at\":\"(.[^\"]*)").matcher(source);
        int index = 0;
        while (matcher.find()) {
            String createdAt = matcher.group(1);
            DateTime dateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(createdAt);
            metadataList.get(index++).setDateTime(dateTime);
        }
        matcher = Pattern.compile("title\":\"(.[^\"]*)").matcher(source);
        index = 0;
        while (matcher.find()) {
            String title = StringEscapeUtils.unescapeJava(matcher.group(1));
            metadataList.get(index++).setTitle(title);
        }
        matcher = Pattern.compile("to\":\"(.[^\"]*)").matcher(source);
        index = 0;
        while (matcher.find()) {
            String recipient = matcher.group(1).replaceAll("[<>]", "");
            metadataList.get(index++).setRecipient(recipient);
        }

        return metadataList;
    }
}
