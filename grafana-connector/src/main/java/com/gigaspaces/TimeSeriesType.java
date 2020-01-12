package com.gigaspaces;

import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

public class TimeSeriesType {
    String displayName;
    String fullTypeName;
    String dateFieldName;
    String valuesFieldName;
    String seriesFieldName;

    public TimeSeriesType(String displayName, String fullTypeName, String dateFieldName, String valuesFieldName, String seriesFieldName) {
        this.displayName = displayName;
        this.fullTypeName = fullTypeName;
        this.dateFieldName = dateFieldName;
        this.valuesFieldName = valuesFieldName;
        this.seriesFieldName = seriesFieldName;
    }

    public TimeSeriesType(String fullTypeName, String dateFieldName, String valuesFieldName, String seriesFieldName) {
        this.fullTypeName = fullTypeName;
        this.dateFieldName = dateFieldName;
        this.valuesFieldName = valuesFieldName;
        this.seriesFieldName = seriesFieldName;
    }

    @Override
    public String toString() {
        return "TimeSeriesType{" +
                "fullTypeName='" + fullTypeName + '\'' +
                ", dateFieldName='" + dateFieldName + '\'' +
                ", valuesFieldName='" + valuesFieldName + '\'' +
                ", seriesFieldName='" + seriesFieldName + '\'' +
                '}';
    }

    @SpaceId
    @SpaceRouting
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public TimeSeriesType() {
    }


    public String getFullTypeName() {
        return fullTypeName;
    }

    public void setFullTypeName(String fullTypeName) {
        this.fullTypeName = fullTypeName;
    }

    public String getDateFieldName() {
        return dateFieldName;
    }

    public void setDateFieldName(String dateFieldName) {
        this.dateFieldName = dateFieldName;
    }

    public String getValuesFieldName() {
        return valuesFieldName;
    }

    public void setValuesFieldName(String valuesFieldName) {
        this.valuesFieldName = valuesFieldName;
    }

    public String getSeriesFieldName() {
        return seriesFieldName;
    }

    public void setSeriesFieldName(String seriesFieldName) {
        this.seriesFieldName = seriesFieldName;
    }
}
