package br.com.flaviowagner.projetos.pix.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter formatter_ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter formatter_ISO_OFFSET_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dataHoraString = p.getText();
        try {
            return LocalDateTime.parse(dataHoraString, formatter_ISO_LOCAL_DATE_TIME);
        }catch (Exception e) {
            return LocalDateTime.parse(dataHoraString, formatter_ISO_OFFSET_DATE_TIME);
        }
    }
}