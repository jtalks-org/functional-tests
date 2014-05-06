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

package org.jtalks.tests.jcommune.mail.pochta;

/**
 * The Data Transfer Object for message
 */
public class Message {
    private String server_id;
    private String envelope_from;
    private String subject;
    private String mail_body;
    private String[] envelope_recipients;

    public String getId() {
        return server_id;
    }

    public void setId(String id) {
        this.server_id = id;
    }

    public String getFrom() {
        return envelope_from;
    }

    public void setFrom(String from) {
        this.envelope_from = from;
    }

    public String getTitle() {
        return subject;
    }

    public void setTitle(String title) {
        this.subject = title;
    }

    public String getRecipient() {
        StringBuilder builder = new StringBuilder();
        for (String s : envelope_recipients) {
            builder.append(s);
        }
        return builder.toString();
    }

    public String getSource() {
        return mail_body;
    }
}
