package com.eyanu.tournamentproject.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;

@Converter
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {
    @Override
    public String convertToDatabaseColumn(ZoneId zoneId) {
        return zoneId.getId();
    }

    @Override
    public ZoneId convertToEntityAttribute(String id) {
        ZoneId zoneId = null;
        try {
            zoneId = ZoneId.of(id);
        } catch (Exception e) {

        }
        return zoneId;
    }
}