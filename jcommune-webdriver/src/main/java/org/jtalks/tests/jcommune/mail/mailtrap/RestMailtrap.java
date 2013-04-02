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

package org.jtalks.tests.jcommune.mail.mailtrap;

import com.google.gson.Gson;
import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.jtalks.tests.jcommune.mail.mailtrap.dto.Message;
import org.jtalks.tests.jcommune.mail.mailtrap.dto.MessageDto;

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
public class RestMailtrap {

    private static final String NOT_FOUND_ID = "no message with this ID";

    private List<Metadata> metadataList;

    public String getActivationLink(String recipient) throws IOException {
        Gson gson = new Gson();
        MessageDto[] messages = gson.fromJson(APIClient.getMessages(),MessageDto[].class);
        metadataList = getMetadataList(messages);

        String id = NOT_FOUND_ID;
        String link = null;
        DateTime createdAt = null;
        for (Metadata metadata : metadataList) {
            if (!recipient.equals(metadata.getRecipient())) {
                continue;
            }
            if (id.equals(NOT_FOUND_ID) || metadata.getDateTime().isAfter(createdAt)) {
                id = metadata.getId();
            }
        }
        if (id.equals(NOT_FOUND_ID)) {
            return link;
        }

        // TODO: extract source value from Mailtrap JSON message (pay attention on \r \n and \" escaped characters) and
        //       construct MimeMessage from it

        try {
            MessageDto messageDto = gson.fromJson(APIClient.getMessage(id),MessageDto.class);
            String source = messageDto.getMessage().getSource();

            MimeMessage message = new MimeMessage(Session.getInstance(new Properties()),
                    (new ByteArrayInputStream(source.getBytes())));
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

    private List<Metadata> getMetadataList(MessageDto[] messages){
        List<Metadata> metadataList = new ArrayList<Metadata>();
        Metadata metadata = null;
        Message message = null;
        for(int i=0; i<messages.length; i++){
            message = messages[i].getMessage();
            metadata = new Metadata();
            metadata.setId(message.getId());
            metadata.setTitle(message.getTitle());
            metadata.setRecipient(message.getRecipients()[0].getRecipient() //TODO: change for array
                    .getTitle().replaceAll("[<>]", ""));
            metadata.setDateTime(ISODateTimeFormat.dateTimeParser().parseDateTime(message.getCreated_at()));
            metadataList.add(metadata);
        }
        return metadataList;
    }
}
