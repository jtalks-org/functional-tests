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
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessageException;
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessagesException;
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.MailWasNotReceivedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jayway.awaitility.Awaitility.await;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

/**
 * Contain operations related to the Maitrap service such as: get activation link sent by JCommune
 *
 * @author Guram Savinov
 */
public class MailtrapMail {
    public static final int MAIL_POLL_INTERVAL = 500;
    private static final Logger LOGGER = LoggerFactory.getLogger(MailtrapMail.class);
    private static final int MAILTRAP_TIMEOUT_SECS = 120;
    private final MailtrapStatistics statistics = MailtrapStatistics.instance();
    private String activationLink;

    /**
     * Get activation link sent by JCommune for the not activated user. Because Mailtrap need any time to receive
     * message getting activation link repeats for {@link #MAILTRAP_TIMEOUT_SECS} seconds with 500 milliseconds
     * interval.
     *
     * @param recipient the recipient email
     * @return the activation link, that user should open to confirm registration
     * @throws MailWasNotReceivedException
     */
    public String getActivationLink(final String recipient) {
        try {
            await().dontCatchUncaughtExceptions().atMost(MAILTRAP_TIMEOUT_SECS, TimeUnit.SECONDS)
                    .pollInterval(MAIL_POLL_INTERVAL, TimeUnit.MILLISECONDS)
                    .until(new Callable<Boolean>() {
                        public Boolean call() throws Exception {
                            LOGGER.debug("Trying to get activation link for email [{}]", recipient);
                            statistics.tryToGetMail(MailtrapMail.this);
                            activationLink = tryToGetLink(recipient);
                            return activationLink != null;
                        }
                    });
        } catch (Exception e) {
            info(String.format(
                    "Failed to get the activation link from mailtrap for user [%s]. See [%s] for the list of messages",
                    recipient, MailtrapClient.mailtrapMessagesUri()));
            statistics.printStatistics(this);
            if (e.getClass().equals(CouldNotGetMessageException.class)) {
                throw (CouldNotGetMessageException) e;
            } else if (e.getClass().equals(CouldNotGetMessagesException.class)) {
                throw (CouldNotGetMessagesException) e;
            } else {
                throw new MailWasNotReceivedException(e);
            }
        }
        statistics.printStatistics(this);
        return activationLink;
    }

    private String tryToGetLink(String recipient) {
        Gson gson = new Gson();
        Message[] messages;
        String link = null;

        messages = gson.fromJson(MailtrapClient.getMessages(), Message[].class);

        Message activationMail = null;
        for (Message message : messages) {
            if (recipient.equalsIgnoreCase(message.getRecipient())) {
                activationMail = message;
            }
        }
        if (activationMail == null) {
            return null;
        }

        try {
            String source = activationMail.getSource();
            Matcher matcher = Pattern.compile("(http://.*/activate/.*)[\\s]").matcher(source);
            if (matcher.find()) {
                link = matcher.group(1);
            }
        } catch (Exception e) {
            LOGGER.warn("Problem occurred while grabbing activation link from Mailtrap", e);
        }
        return link;
    }
}
