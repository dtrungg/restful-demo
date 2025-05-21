package org.example.restfuldemo.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AesImpl {
    private static final String CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE = 128;
    private static final int GCM_TAG_LENGTH = 128; // 128-bit authentication tag
    private static final int GCM_IV_LENGTH = 12;   // 12-byte IV (unchanged)
    private static final int SRC_POSITION = 0;
    private static final int DEST_POSITION = 0;

    public static String generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE);
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String encrypt(String plaintext, String keyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        byte[] ciphertext = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, SRC_POSITION, combined, DEST_POSITION, iv.length);
        System.arraycopy(ciphertext, SRC_POSITION, combined, iv.length, ciphertext.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public static String decrypt(String base64Encrypted, String keyString) throws Exception {
        byte[] combined = Base64.getDecoder().decode(base64Encrypted);
        byte[] keyBytes = Base64.getDecoder().decode(keyString);
        SecretKey key = new SecretKeySpec(keyBytes, "AES");

        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(combined, SRC_POSITION, iv, DEST_POSITION, iv.length);

        byte[] cipherText = new byte[combined.length - iv.length];
        System.arraycopy(combined, iv.length, cipherText, DEST_POSITION, cipherText.length);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String key = generateKey();
        System.out.println(key);
        //        SecretKey key = generateKeyFromPassword("your_password", AES_KEY_SIZE);

        String plaintext = "API Secret Data";
        System.out.println(plaintext);
        String encrypted = encrypt(plaintext, key);
        System.out.println("Encrypted (AES-128): " + encrypted);

        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted: " + decrypted);
    }

    private static SecretKey generateKeyFromPassword(String password, int keyLength) throws Exception {
        // Validate key length (128, 192, or 256 bits)
        if (keyLength != 128 && keyLength != 192 && keyLength != 256) {
            throw new IllegalArgumentException("Invalid key length. Use 128, 192, or 256.");
        }

        // Generate a random salt (should be stored securely with the ciphertext)
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Configure PBKDF2 parameters
        int iterations = 10000; // Recommended minimum
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] secretKeyBytes = factory.generateSecret(spec).getEncoded();

        // Return as a SecretKey
        return new javax.crypto.spec.SecretKeySpec(secretKeyBytes, "AES");
    }
}
