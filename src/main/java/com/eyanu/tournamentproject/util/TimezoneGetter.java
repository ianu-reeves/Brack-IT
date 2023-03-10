package com.eyanu.tournamentproject.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

public class TimezoneGetter {
    private TimezoneGetter(){}

    public static Map<String, String> getTimezones() {
        List<String> zoneIds = new ArrayList<>(ZoneId.getAvailableZoneIds());
        Collections.sort(zoneIds);
        Map<String, String> zones = new TreeMap<>();
        ZoneOffset zoneOffset;
        for (String zoneId : zoneIds) {
            zoneOffset = ZoneId.of(zoneId).getRules().getOffset(Instant.now());
            zones.put(zoneId, (zoneOffset==ZoneOffset.UTC) ? "+0:00" : zoneOffset.toString());
        }

        return zones;
    }
}
