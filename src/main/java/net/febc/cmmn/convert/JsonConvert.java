package net.febc.cmmn.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON Colum을 변경
 */
public class JsonConvert implements AttributeConverter<Map<String, String>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        try {
            return new ObjectMapper().writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {
        if (StringUtils.hasText(dbData)){
            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse( dbData );
                return new ObjectMapper().readValue(obj.toString(), Map.class);
            } catch (ParseException |
                     JsonProcessingException e) {
                return new HashMap<>();
            }
        }
        return new HashMap<>();
    }
}
