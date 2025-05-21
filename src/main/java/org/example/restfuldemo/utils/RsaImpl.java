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
        //        generateKeyPair();
        // Signing demo
        //        String signature = sign(originalText);
        //        boolean isValid = verify(originalText, signature);
        //        System.out.println("Signature valid: " + isValid);

        //        String encrypted = encrypt(originalText);
        //        String decrypted = decrypt(encrypted);
        //        System.out.println("encrypted: " + encrypted);
        //        System.out.println("Original: " + originalText);
        //        System.out.println("Decrypted: " + decrypted);

        //        System.out.println("publicKey: " + getPublicKeyBase64());
        //        System.out.println("privateKey: " + getPrivateKeyBase64());

        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnys2Kz4By1Iib4UcMixgj3nbuZrN1Qwt3bWMAXaOBf8Ycak03GmECQRdS61N+0gWQgJCMnpDN9f4vq0BdKPzdC3PjIUWhiOkv7hOIUq+/4gDrs156FI4re3Z8Uav9C/XKTqHhhGgDo1pu8Ddy+QeDy/vDgt2TotNhcl3bv3VpZwPfb0VVrIDSRZbEmAjEPhc3XohQMa1vVlKvRdSlwHvvhlw6o5smXRPQ9jTSK6Z3YjJlC5oP3cyImZBWJBV4Bdt+5S+JRYNlBUgz4zeHBa406C+Mmp/mDZ9dv2CSWMWjTtZyWta617c0YxqaCWtaF0fGeSyg/CdlUEcQZbQ1ZX/qwIDAQAB";
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCfKzYrPgHLUiJvhRwyLGCPedu5ms3VDC3dtYwBdo4F/xhxqTTcaYQJBF1LrU37SBZCAkIyekM31/i+rQF0o/N0Lc+MhRaGI6S/uE4hSr7/iAOuzXnoUjit7dnxRq/0L9cpOoeGEaAOjWm7wN3L5B4PL+8OC3ZOi02FyXdu/dWlnA99vRVWsgNJFlsSYCMQ+FzdeiFAxrW9WUq9F1KXAe++GXDqjmyZdE9D2NNIrpndiMmULmg/dzIiZkFYkFXgF237lL4lFg2UFSDPjN4cFrjToL4yan+YNn12/YJJYxaNO1nJa1rrXtzRjGpoJa1oXR8Z5LKD8J2VQRxBltDVlf+rAgMBAAECggEAEBtnV+jmdZTSN0bZG5qyCtLiEV5BBhY9bfo5OMyugpzV9GqTFtr2qZLvfgEDqzIYToW7/S+yLayFV4ZaZ1tP/6nDHw04wkpJGb2k6mmN+n3hIYdZltD7O16ed3QXyjYtJ9bsb/zInbBhysYxq7hh8ld/cYRnbHtcVPWWSH0XabLAWXflxYK16qYXy4/WMHTc6jPOEusllY28JJHJKeiy9klzdkgiTuMtiXOIADNDEWryQ7q3+mtkd2ZdBEPH+efTXJRaaN7IPYZi8MArQirzLylVkBxXFKpRVO53ysqU4OzeOzRE5HfQvx+GCTMYXJfIVzT2VTahXWSK/qMc9f0cgQKBgQDBHU403P6QRsp1D/lslWYWZlqhz+q6x/QDbJfPgcxvtKcH+17/K/daRSEfQieJvgVhkJHd6bE7wVqeOThrmenO6a1mKWBhBwFgXwNGzXk8yO1mHHWOkJxmL8HjUjCJT0jQ6QOeYktMUNV0hqmw4GGYQKy9nwm6ZSdRxugZ8w2BqQKBgQDTABN/61KpJuO8kMZgAXmsGTfh/8oM159EwUrLM2ZpiIEqQc5wjbNx4fUZeoM7LN5co7B4OdnE3MRw/pRHAVJ5iM4PhWQbPwCWea5+NCCFOXbx0LX58j2GyT9z/jVX/fUJpVOpDV9UU55dnJO0SFzrP9ihbr8bPDmVPeu4E9yzMwKBgDyysF0vRRgmGAaZYcV+Y9sQitYnWtkgdhMJc+H2I0LNHl8E5IQB1rpZ1iJUEcdaoahb321HwbxNduUaiSBQ/Uta6XSd9rwojocy9f2wIbY4VV3t/gk0bOQ1iJShxb2y3OlFBC0pKg+vrJf2p6l/rU1ErsbfERi0KJqskVFussUZAoGANJkTQ1MVIweHw8zPNsWHYChVLuhFpNfnYwtQKsFgaTRXBoKrBJG+RshOAhDxP8IamDtR1VaOe62Q5p3/toeU2KkKvQHCh6w36Eri/aDe7nb5uKQPiQQi5EOspqya/ZlYCRSGe2DS/8Fse+d5Rnq3dwlOZkemKhLkQ9jQtu10mdMCgYEAhjofGrv/KiYy/MCzribI3tznLZJw14ve/jevB8d3C3TwqLD426jvsIgDcGtGbm+mF8sEg6e2ND/5N01Ihjod8td1Zc3x3dqJDSrfXhmKbWr/4cPBUysAkO0qxmk2LGs3mhrBkkJAVDelCfTWzEVMJ91KHTqnukXv12dXhbMyS2E=";

        String accSrc = encrypt("acc1", publicKey);
        String accDest = encrypt("acc2", publicKey);
        String amount = encrypt("20.00", publicKey);
        System.out.println("accSrc: " + accSrc);
        System.out.println("accDest: " + accDest);
        System.out.println("amount: " + amount);
    }
}
