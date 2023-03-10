package com.eyanu.tournamentproject.enums;

public enum SortMethod {
    REGION("REGION", "region"),
    CREATION_DATE("CREATION_DATE", "creation date"),
    START_DATE("START_DATE", "start date"),
    ENROLLMENT_DATE("ENROLLMENT_DATE", "enrollment date");

    private final String columnName;
    private final String readableName;

    SortMethod(String columnName, String readableName) {
        this.columnName = columnName;
        this.readableName = readableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getReadableName() {
        return readableName;
    }
}
