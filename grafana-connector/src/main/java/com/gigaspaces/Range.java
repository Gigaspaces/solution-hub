package com.gigaspaces;

import javax.json.JsonObject;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *
 */
public class Range {

    private long start;
    private long end;
    private long duration;

    public Range(long start, long end) {

        this.start = start;
        this.end = end;
        this.duration = end - start;
    }

    public static Range from(JsonObject range) {

        if(range == null) {
            return new Range(System.currentTimeMillis(), System.currentTimeMillis());
        }
        String fromString = range.getString("from");
        String toString = range.getString("to");

        //2016-10-18T13:16:33.733Z
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime fromDateTime = LocalDateTime.parse(fromString, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(toString, formatter);

        return new Range(fromDateTime.toInstant(ZoneOffset.UTC).toEpochMilli(),
                toDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public long getStart() {

        return start;
    }

    public long getEnd() {

        return end;
    }

    public long getDuration() {

        return duration;
    }
}