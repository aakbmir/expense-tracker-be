package com.aakbmir.expensetracker.utils.validators;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;

import static com.aakbmir.expensetracker.config.BusinessClock.BUSINESS_ZONE;

public class GstDateTimeSerializer
        extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(
            ZonedDateTime value,
            JsonGenerator gen,
            SerializerProvider serializers)
            throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }

        ZonedDateTime gstValue =
                value.withZoneSameInstant(
                        BUSINESS_ZONE
                );

        gen.writeString(gstValue.toOffsetDateTime().toString());
    }
}