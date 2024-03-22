package egovframework.cmmn.convert;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextListConvert implements AttributeConverter<List<String>, String> {

    private static final String SEPARATOR = ",";
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {

        // 데이터가 존재하지 않으면 ""
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return StringUtils.join(attribute, SEPARATOR);
   }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        // 문자열이 없거나 공백의 경우 반환
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        String[] data = dbData.split(SEPARATOR);
        return new ArrayList<>(Arrays.asList(data));
    }
}
