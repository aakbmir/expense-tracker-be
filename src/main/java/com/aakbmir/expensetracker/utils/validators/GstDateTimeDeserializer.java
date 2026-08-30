package com.aakbmir.expensetracker.utils.validators;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.aakbmir.expensetracker.config.BusinessClock.BUSINESS_ZONE;

public class GstDateTimeDeserializer
        extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(
            JsonParser parser,
            DeserializationContext context)
            throws IOException {

        String value = parser.getText();

        if (value == null || value.isBlank()) {
            return null;
        }

        ZonedDateTime parsed =
                ZonedDateTime.parse(value);

        return parsed.withZoneSameInstant(
                BUSINESS_ZONE
        );
    }
}