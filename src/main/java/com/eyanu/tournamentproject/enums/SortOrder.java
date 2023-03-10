package com.eyanu.tournamentproject.enums;

public enum SortOrder {
    ASC("ASC", "ascending"),
    DESC("DESC", "descending");

    private final String abbreviation;
    private final String fullName;

    SortOrder(String abbreviation, String fullName) {
        this.abbreviation = abbreviation;
        this.fullName = fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getFullName() {
        return fullName;
    }
}
