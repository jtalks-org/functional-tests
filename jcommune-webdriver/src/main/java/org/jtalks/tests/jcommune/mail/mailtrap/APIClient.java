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

import org.restlet.resource.ClientResource;

import java.io.IOException;

/**
 * @author Guram Savinov
 */
public class APIClient {

    private static final String API_INBOXES_URL = "http://mailtrap.io/api/v1/inboxes/";
    private static final String JTALKS_AUTOTESTS_MESSAGES = "jtalks-autotests/messages/";
    private static final String API_TOKEN_PARAM = "?token=fWJsZAhSzI4Ghb9cE42ouA";

    public static String getMessages() throws IOException {
        ClientResource client = new ClientResource(API_INBOXES_URL + JTALKS_AUTOTESTS_MESSAGES + API_TOKEN_PARAM);
        return client.get().getText();
    }

    public static String getMessage(String id) throws IOException {
        ClientResource client = new ClientResource(API_INBOXES_URL + JTALKS_AUTOTESTS_MESSAGES + id + API_TOKEN_PARAM);
        return client.get().getText();
    }
}
