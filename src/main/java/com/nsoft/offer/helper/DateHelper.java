package com.nsoft.offer.helper;

import java.time.Instant;
import java.util.Date;

public class DateHelper {

    public static Date getValidDate(Date date) {
        return date == null ? date = Date.from(Instant.now()) : date;
    }

}
