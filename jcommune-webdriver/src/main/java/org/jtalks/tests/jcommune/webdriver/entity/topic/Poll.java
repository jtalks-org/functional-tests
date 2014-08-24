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

package org.jtalks.tests.jcommune.webdriver.entity.topic;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jtalks.tests.jcommune.utils.TestStringUtils;

/**
 * Jcommune poll representation
 *
 * @author Guram Savinov
 */
public class Poll {
    private static final String POLL_END_DATE_FORMAT = "dd-MM-yyyy";
    private String title;
    private String[] options = {TestStringUtils.randomString(10), TestStringUtils.randomString(10)};
    private Date endDate;
    private boolean multipleAnswers;

    public Poll(String title) {
        this.title = title;
    }

    public Poll(String title, String[] options) {
        this.title = title;
        this.options = options;
    }

    public Poll withNumberOfOptions(int number){
        this.options = new String[number];
        for (int i = 0; i < number; i++)
            this.options[i] = TestStringUtils.randomString(10);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        return options;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndDateAsString() {
        if (endDate != null) {
            return new SimpleDateFormat(POLL_END_DATE_FORMAT).format(endDate);
        }
        return null;
    }

    public boolean isMultipleAnswers() {
        return multipleAnswers;
    }

    public void setMultipleAnswers(boolean multipleAnswers) {
        this.multipleAnswers = multipleAnswers;
    }

}
