package com.aakbmir.expensetracker.utils.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Converter(autoApply = false)
public class GstUtcConverter
        implements AttributeConverter<ZonedDateTime, ZonedDateTime> {

    private static final ZoneId GST_ZONE =
            ZoneId.of("Asia/Dubai");

    @Override
    public ZonedDateTime convertToDatabaseColumn(
            ZonedDateTime attribute) {

        if (attribute == null) {
            return null;
        }

        // Convert Dubai/local time to UTC
        return attribute
                .withZoneSameInstant(ZoneId.of("UTC"));
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(
            ZonedDateTime dbData) {

        if (dbData == null) {
            return null;
        }

        // Convert UTC to Dubai time
        return dbData
                .withZoneSameInstant(GST_ZONE);
    }
}