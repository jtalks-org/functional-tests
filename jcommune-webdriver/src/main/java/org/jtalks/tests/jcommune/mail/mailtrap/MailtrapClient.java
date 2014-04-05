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

import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessageException;
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessagesException;
import org.restlet.engine.Engine;
import org.restlet.ext.slf4j.Slf4jLoggerFacade;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * This class contains operations for getting data from Mailtrap service by REST API: get inbox metadata,
 * get list of messages metadata, get message by identifier
 *
 * @author Guram Savinov
 */
public class MailtrapClient {

    private static final String API_INBOXES_URL = "https://mailtrap.io/api/v1/inboxes/";
    private static final String JTALKS_AUTOTESTS_MESSAGES = "14272/messages/";
    private static final String API_TOKEN_PARAM = "?api_token=089dbeb32c12ea062be1e1d87cef3267";

    static {
        Slf4jLoggerFacade loggerFacade = new Slf4jLoggerFacade();
        Engine.getInstance().setLoggerFacade(loggerFacade);//by default it will use Java SE logging
    }

    /**
     * Gets list of messages metadata
     *
     * @return the list of messages metadata
     * @throws CouldNotGetMessagesException
     */
    public static String getMessages() throws CouldNotGetMessagesException {
        RestTemplate client = new RestTemplate();
        try {
            return client.getForObject(new URI(mailtrapMessagesUri()), String.class);
        } catch (Exception e) {
            throw new CouldNotGetMessagesException(e);
        }
    }

    public static String mailtrapMessagesUri() {
        return API_INBOXES_URL + JTALKS_AUTOTESTS_MESSAGES + API_TOKEN_PARAM;
    }

    /**
     * Get message by identifier
     *
     * @param id the UUID type identifier
     * @return the message
     * @throws CouldNotGetMessageException
     */
    public static String getMessage(String id) {
        RestTemplate client = new RestTemplate();
        try {
            return client.getForObject(new URI(API_INBOXES_URL + JTALKS_AUTOTESTS_MESSAGES + id + API_TOKEN_PARAM),
                    String.class);
        } catch (Exception e) {
            throw new CouldNotGetMessageException(e);
        }
    }
}
