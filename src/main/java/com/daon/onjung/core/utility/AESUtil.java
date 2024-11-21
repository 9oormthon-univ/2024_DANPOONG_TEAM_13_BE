package com.daon.onjung.core.utility;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESUtil {

    @Value("${aes-util.algorithm}")
    private String algorithm;

    @Value("${aes-util.secret-key}")
    private String secret_key;

    private static final SecureRandom random = new SecureRandom();

    // 랜덤 16바이트 IV 생성
    public byte[] generateRandomIV() {
        byte[] iv = new byte[16]; // 16바이트 IV
        random.nextBytes(iv);
        return iv;
    }

    // 공통된 암호화/복호화 처리
    private Cipher getCipher(int mode, byte[] keyBytes, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(mode, secretKeySpec, ivParameterSpec);
        return cipher;
    }

    // AES 암호화
    public String encrypt_AES(String id) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secret_key);
            byte[] iv = generateRandomIV();

            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, keyBytes, iv);
            byte[] encrypted = cipher.doFinal(id.getBytes());

            // IV와 암호화된 데이터를 결합 (IV + Ciphertext)
            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            throw new CommonException(ErrorCode.AES_ENCRYPTION_ERROR);
        }
    }

    // AES 복호화
    public String decrypt_AES(String encryptedText) {
        try {
            byte[] combined = Base64.getDecoder().decode(encryptedText);
            byte[] keyBytes = Base64.getDecoder().decode(secret_key);

            // IV와 암호화된 데이터 분리
            byte[] iv = new byte[16];
            byte[] encrypted = new byte[combined.length - 16];
            System.arraycopy(combined, 0, iv, 0, 16);
            System.arraycopy(combined, 16, encrypted, 0, encrypted.length);

            Cipher cipher = getCipher(Cipher.DECRYPT_MODE, keyBytes, iv);
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);

        } catch (Exception e) {
            throw new CommonException(ErrorCode.AES_DECRYPTION_ERROR);
        }
    }
}