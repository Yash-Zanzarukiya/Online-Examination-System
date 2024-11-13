package com.yashpz.examination_system.examination_system.mappers;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {

    @Override
    public String convertToDatabaseColumn(Duration duration) {
        return (duration == null ? null : duration.toString());
    }

    @Override
    public Duration convertToEntityAttribute(String durationString) {
        return (durationString == null ? null : Duration.parse(durationString));
    }
}
