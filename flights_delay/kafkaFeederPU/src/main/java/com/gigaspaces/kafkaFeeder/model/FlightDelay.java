package com.gigaspaces.kafkaFeeder.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightDelay {

    private Double year;
    private Double month;
    private Double dayofMonth;
    private Double dayOfWeek;
    private String carrier;
    private String tail_number;
    private String flight_number;
    private String origin;
    private String dest;
    private String crsDepTime;
    private Double depDelay;
    private Double depDelay15;
    private Double cancelled;
    private String awnd;
    private String prcp;
    private String snow;
    private String tmax;
    private String tmin;

    public static FlightDelay fromCsvStr(String csvStr) {
        String[] fields = csvStr.split(",");
        int fieldIndex = 0;
        return new FlightDelayBuilder()
                .year(Double.parseDouble(fields[fieldIndex++]))
                .month(Double.parseDouble(fields[fieldIndex++]))
                .dayofMonth(Double.parseDouble(fields[fieldIndex++]))
                .dayOfWeek(Double.parseDouble(fields[fieldIndex++]))
                .carrier(fields[fieldIndex++])
                .tail_number(fields[fieldIndex++])
                .flight_number(fields[fieldIndex++])
                .origin(fields[fieldIndex++])
                .dest(fields[fieldIndex++])
                .crsDepTime(fields[fieldIndex++])
                .depDelay(Double.parseDouble((fields[fieldIndex].length()==0)?"0":fields[fieldIndex]))
                .depDelay15(Double.parseDouble((fields[++fieldIndex].length()==0)?"0":fields[fieldIndex]))
                .cancelled(Double.parseDouble((fields[++fieldIndex].length()==0)?"0":fields[fieldIndex]))
                .awnd(fields[fieldIndex++])
                .prcp(fields[fieldIndex++])
                .snow(fields[fieldIndex++])
                .tmax(fields[fieldIndex++])
                .tmin(fields[fieldIndex++])
                .build();
    }
}
