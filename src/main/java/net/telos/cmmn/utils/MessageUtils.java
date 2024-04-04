package net.telos.cmmn.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 메세지(messages.properties에 존재하는 메세지 가져오기)
 */
@Component
public class MessageUtils {
    private static MessageSource messageSource;

    @Autowired
    private void initMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static String getMessage(String messageCd) {
        return messageSource.getMessage(messageCd, null, Locale.KOREAN);
    }

    public static String getMessage(String messageCd, Object[] messageArgs) {
        return messageSource.getMessage(messageCd, messageArgs, Locale.KOREAN);
    }
}

