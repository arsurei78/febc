package net.febc.cmmn.convert;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class GrantedAuthorityConverter implements AttributeConverter<GrantedAuthority, String> {
    @Override
    public String convertToDatabaseColumn(GrantedAuthority attribute) {
        return attribute.getAuthority();
    }

    @Override
    public GrantedAuthority convertToEntityAttribute(String role) {
        if (StringUtils.hasText(role)) {
            return new SimpleGrantedAuthority(role);
        }
        return null;
    }
}
