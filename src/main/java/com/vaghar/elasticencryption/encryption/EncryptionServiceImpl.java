package com.vaghar.elasticencryption.encryption;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class EncryptionServiceImpl {

    private static final String AES = "AES";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final int IV_SIZE = 12;
    private static final int TAG_SIZE = 128;
    private static final String SECRET_KEY = "1234567890123456"; // 16 bytes for AES-128 (Use a real secret manager in production)

    public static String hashForSearch(String keyword) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashed = digest.digest(keyword.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hashed);
    }

    public String encrypt(String plaintext) throws Exception {
        byte[] iv = new byte[IV_SIZE];
        java.security.SecureRandom.getInstanceStrong().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), AES);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] encrypted = cipher.doFinal(plaintext.getBytes());
        byte[] encryptedIVAndText = new byte[IV_SIZE + encrypted.length];

        System.arraycopy(iv, 0, encryptedIVAndText, 0, IV_SIZE);
        System.arraycopy(encrypted, 0, encryptedIVAndText, IV_SIZE, encrypted.length);

        return Base64.getEncoder().encodeToString(encryptedIVAndText);
    }

    public String decrypt(String encrypted) throws Exception {
        byte[] encryptedIvTextBytes = Base64.getDecoder().decode(encrypted);

        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);

        int encryptedSize = encryptedIvTextBytes.length - IV_SIZE;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, IV_SIZE, encryptedBytes, 0, encryptedSize);

        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
        SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), AES);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, iv);

        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        byte[] decrypted = cipher.doFinal(encryptedBytes);

        return new String(decrypted);
    }
}
