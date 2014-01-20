package org.jtalks.tests.jcommune.mail.mailtrap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

/**
 * The class is needed to gather mail statistics - how fast do we get responses from Mailtrap. So that we can discuss
 * performance problems with the Mailtrap team.
 * We'll decide whether to write our own implementation if the performance will not satisfy us.
 */
public class MailtrapStatistics {
    private final static MailtrapStatistics INSTANCE = new MailtrapStatistics();
    private final Map<Object, AtomicInteger> numberOfTrials = new ConcurrentHashMap<>();

    private MailtrapStatistics() {
    }

    public static MailtrapStatistics instance() {
        return INSTANCE;
    }

    public void tryToGetMail(MailtrapMail callable) {
        AtomicInteger numberOfTrialsForParticularUser = numberOfTrials.get(callable);
        if (numberOfTrialsForParticularUser == null) {
            numberOfTrialsForParticularUser = new AtomicInteger(0);
        }
        numberOfTrialsForParticularUser.incrementAndGet();
        numberOfTrials.put(callable, numberOfTrialsForParticularUser);
    }

    public void printStatistics(MailtrapMail callable) {
        AtomicInteger numberOfTrialsForParticularNotification = numberOfTrials.get(callable);
        if (numberOfTrialsForParticularNotification == null) {
            numberOfTrialsForParticularNotification = new AtomicInteger(0);
        }
        info("Mailtrap statistics: ");
        info(String.format("Current mail notification was received in %d trials which took %,d millisecs",
                numberOfTrialsForParticularNotification.get(),
                numberOfTrialsForParticularNotification.get() * MailtrapMail.MAIL_POLL_INTERVAL));
        info("Overall there were [" + overallNumberOfTrials() + "] number of trials" +
                " to get all the mails during this run so far to get [" + numberOfTrials.size() + "] mails it total");
        info(String.format("The average number of trials is [%d] which is [%,d] millisecs",
                averageNumberOfTrials(), averageNumberOfTrials() * MailtrapMail.MAIL_POLL_INTERVAL));
        info(String.format("The minimum time it took: [%,d] millisecs, the maximum was [%,d]",
                minimumTimeToGetMail(), maximumTimeToGetMail()));
    }

    private int overallNumberOfTrials() {
        int overallNumberOfTrials = 0;
        for (AtomicInteger number : numberOfTrials.values()) {
            overallNumberOfTrials += number.get();
        }
        return overallNumberOfTrials;
    }

    private int averageNumberOfTrials() {
        return overallNumberOfTrials() / numberOfTrials.size();
    }

    private long minimumTimeToGetMail() {
        int minNumberOfTrials = Integer.MAX_VALUE;
        for (AtomicInteger number : numberOfTrials.values()) {
            if (number.get() < minNumberOfTrials) {
                minNumberOfTrials = number.get();
            }
        }
        return minNumberOfTrials * MailtrapMail.MAIL_POLL_INTERVAL;
    }

    private long maximumTimeToGetMail() {
        int maxNumberOfTrials = -1;
        for (AtomicInteger number : numberOfTrials.values()) {
            if (number.get() > maxNumberOfTrials) {
                maxNumberOfTrials = number.get();
            }
        }
        return maxNumberOfTrials * MailtrapMail.MAIL_POLL_INTERVAL;
    }
}
