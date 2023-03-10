package com.eyanu.tournamentproject.enums;

public enum Region {
    NA("NA", "North America"),
    SA("SA", "South America"),
    EU("EU", "Europe"),
    ME("ME", "Middle East"),
    AF("AF", "Africa"),
    AS("AS", "Asia"),
    OC("OC", "Oceania");

    private final String shortName;
    private final String fullName;

    Region(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }
}
