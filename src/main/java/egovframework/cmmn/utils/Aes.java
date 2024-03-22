package egovframework.cmmn.utils;

import egovframework.cmmn.constant.ApplicationConstants;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Mysql암호화 / 복호화
 */
public class Aes {

    private static String cipherAes = "AES/ECB/PKCS5Padding";

    private Aes() {}

    /**
     * MariaDB 암호화
     * @param text
     * @return
     */
    public static String encryption(String text) {
        if(StringUtils.isBlank(text)) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance(cipherAes);

            byte[] key = new byte[16];
            int i = 0;

            for(byte b : ApplicationConstants.aesKey.getBytes(StandardCharsets.UTF_8)) {
                key[i++%16] ^= b;
            }
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            return new String(Hex.encodeHex(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)))).toUpperCase();
        } catch(NoSuchPaddingException |
                NoSuchAlgorithmException |
                IllegalBlockSizeException |
                BadPaddingException |
                InvalidKeyException e) {
            return text;
        }
    }

    /**
     * MariaDB 복호화
     *
     * @param encryptedText
     * @return
     */
    public static String decryption(String encryptedText) {
        if(StringUtils.isBlank(encryptedText)) {
            return "";
        }
        try {
            final Cipher cipher = Cipher.getInstance(cipherAes);
            byte[] key = new byte[16];
            int i = 0;
            for(byte b : ApplicationConstants.aesKey.getBytes(StandardCharsets.UTF_8)) {
                key[i++%16] ^= b;
            }
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            return new String(cipher.doFinal(Hex.decodeHex(encryptedText.toCharArray())));
        } catch(NoSuchPaddingException |
                NoSuchAlgorithmException |
                InvalidKeyException |
                DecoderException |
                IllegalBlockSizeException |
                BadPaddingException e) {
            return encryptedText;
        }
    }
}
