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
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessageException;
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessagesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Contain operations related to the Maitrap service such as: get activation link sent by JCommune
 *
 * @author Guram Savinov
 */
public class MailtrapMail {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailtrapMail.class);
    private static final String NOT_FOUND_ID = "no message with this ID";

    /**
     * Get activation link sent by JCommune for not activated user
     *
     * @param recipient the recipient email
     * @throws CouldNotGetMessagesException
     * @throws CouldNotGetMessageException
     * @return the activation link, that user should open to confirm registration
     */
    public static String getActivationLink(String recipient) throws CouldNotGetMessagesException,
            CouldNotGetMessageException {
        Gson gson = new Gson();
        MessageDto[] messages;
        String link = null;

        messages = gson.fromJson(MailtrapClient.getMessages(),MessageDto[].class);

        List<Metadata> metadataList = getMetadataList(messages);

        String id = NOT_FOUND_ID;
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

        MessageDto messageDto = gson.fromJson(MailtrapClient.getMessage(id),MessageDto.class);

        try {
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
            LOGGER.warn("Problem occurred while grabbing activation link from Mailtrap",e);
        } catch (IOException e) {
            LOGGER.warn("Problem occurred while grabbing activation link from Mailtrap",e);
        }
        return link;
    }

    private static List<Metadata> getMetadataList(MessageDto[] messageDtoArray) {
        List<Metadata> metadataList = new ArrayList<Metadata>();
        Metadata metadata;
        for (MessageDto messageDto : messageDtoArray) {
            Message message = messageDto.getMessage();
            metadata = new Metadata();
            metadata.setId(message.getId());
            metadata.setTitle(message.getTitle());
            metadata.setRecipient(message.getRecipients()[0].getRecipient()
                    .getTitle().replaceAll("[<>]", ""));
            metadata.setDateTime(ISODateTimeFormat.dateTimeParser().parseDateTime(message.getCreated_at()));
            metadataList.add(metadata);
        }
        return metadataList;
    }
}
