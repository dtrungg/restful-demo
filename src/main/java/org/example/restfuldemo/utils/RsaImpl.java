package org.example.restfuldemo.utils;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class RsaImpl {
    //    private PrivateKey privateKey;
    //    private PublicKey publicKey;

    //    public RsaImpl() throws NoSuchAlgorithmException {
    //        generateKeyPair();
    //    }
    private static final int KEY_SIZE = 2048;
    private static final String RSA = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    public static void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA);
        SecureRandom random = new SecureRandom();
        keyGen.initialize(KEY_SIZE, random);
        KeyPair pair = keyGen.generateKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
    }

    // Encryption with a public key provided as a Base64 string
    public static String encrypt(
            String plainText,
            String base64PublicKey
    ) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, GeneralSecurityException {
        PublicKey publicKey = loadPublicKeyFromBase64(base64PublicKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decryption with a private key provided as a Base64 string
    public static String decrypt(
            String encryptedText,
            String base64PrivateKey
    ) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, GeneralSecurityException {
        PrivateKey privateKey = loadPrivateKeyFromBase64(base64PrivateKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // Signing with a private key provided as a Base64 string
    public static String sign(
            String data,
            String base64PrivateKey
    ) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, GeneralSecurityException {
        PrivateKey privateKey = loadPrivateKeyFromBase64(base64PrivateKey);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // Verification with a public key provided as a Base64 string
    public static boolean verify(String data, String signature, String base64PublicKey) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException, GeneralSecurityException {
        PublicKey publicKey = loadPublicKeyFromBase64(base64PublicKey);
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = Base64.getDecoder().decode(signature);
        return sig.verify(signatureBytes);
    }

    // Helper method to load a public key from a Base64 string
    private static PublicKey loadPublicKeyFromBase64(String base64PublicKey) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(spec);
    }

    // Helper method to load a private key from a Base64 string
    private static PrivateKey loadPrivateKeyFromBase64(String base64PrivateKey) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(spec);
    }

    public static String getPublicKeyBase64() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static String getPrivateKeyBase64() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    // Example usage
    public static void main(String[] args) throws Exception {
        RsaImpl rsa = new RsaImpl();
        generateKeyPair();

        String originalText = "Secret API Data";

        // Encryption demo
        //        String encrypted = encrypt(originalText);
        //        String decrypted = decrypt(encrypted);
        //        System.out.println("encrypted: " + encrypted);
        //        System.out.println("Original: " + originalText);
        //        System.out.println("Decrypted: " + decrypted);

        // Signing demo
        //        String signature = sign(originalText);
        //        boolean isValid = verify(originalText, signature);
        //        System.out.println("Signature valid: " + isValid);
        System.out.println("publicKey: " + getPublicKeyBase64());
        System.out.println("privateKey: " + getPrivateKeyBase64());
    }
}
