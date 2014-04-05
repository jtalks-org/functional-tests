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

package org.jtalks.tests.jcommune.mail.mailtrap.dto;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 *  The Data Transfer Object for message
 */
public class Message {
    private String id;
    private String from_email;
    private String subject;
    private String text_body;
    private String sent_at;
    private String to_email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from_email;
    }

    public void setFrom(String from) {
        this.from_email = from;
    }

    public String getTitle() {
        return subject;
    }

    public void setTitle(String title) {
        this.subject = title;
    }

    public DateTime getCreated_at() {
        return ISODateTimeFormat.dateTimeParser().parseDateTime(sent_at);
    }

    public void setCreated_at(String created_at) {
        this.sent_at = created_at;
    }

    public String getRecipient() {
        return to_email;
    }

    public void setRecipient(String recipients) {
        this.to_email = recipients;
    }

    public String getSource() {
        return text_body;
    }

    public void setSource(String text_body) {
        this.text_body = text_body;
    }
}
