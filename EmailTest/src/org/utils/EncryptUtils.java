package org.utils;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypt Utils
 */
public class EncryptUtils {
    public static final String HASH_ALGORITHM = "SHA-1";

    public static final int HASH_INTERATIONS = 1024;

    private static final int SALT_SIZE = 8;

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY = "MySuperSecretKey".getBytes();

    public static String encrypt(String str){
        // do some encryption
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            return Base64.encodeBase64String(c.doFinal(str.getBytes()));
            //return Base64.encodeBytes(c.doFinal(str.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decode(String str){
        // do some decryption
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.decodeBase64(str)));
            //return new String(c.doFinal(Base64.decode(str)));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Encrypt password
     *
     * @param plainPassword : plain password
     * @return password
     */
    public static String encryptPassword(String plainPassword) {
        byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
        byte[] hashPassword = DigestUtils.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return EncodeUtils.encodeHex(salt) + EncodeUtils.encodeHex(hashPassword);
    }

    /**
     * Validate password
     *
     * @param plainPassword : plain password
     * @param password      : password
     * @return : True / False
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = EncodeUtils.decodeHex(password.substring(0, 16));
        byte[] hashPassword = DigestUtils.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(EncodeUtils.encodeHex(salt) + EncodeUtils.encodeHex(hashPassword));
    }

    public static boolean validatePassword2(String plainPassword, String password) {
        return password.equals(EncryptUtils.md5(plainPassword));
    }

    public static boolean validatePasswordByMD5(String plainPassword, String password) {
        return password.equals(EncryptUtils.md5(plainPassword));
    }
    /**
     * Encrypt String
     *
     * @param inputText : input text
     * @return : String
     */
    public static String e(String inputText) {
        return md5(inputText);
    }

    /**
     * Encrypt String
     *
     * @param inputText : String
     * @return : String
     */
    public static String md5AndSha(String inputText) {
        return sha(md5(inputText));
    }

    /**
     * Encrypt String
     *
     * @param inputText : String
     * @return : String
     */
    public static String md5(String inputText) {
        return encrypt(inputText, "MD5");
    }

    /**
     * Encrypt String
     *
     * @param inputText : String
     * @return : String
     */
    public static String sha(String inputText) {
        return encrypt(inputText, "sha-1");
    }

    /**
     * Encrypt String
     *
     * @param inputText     : String
     * @param algorithmName : Algorithm (sha-1 / md5)
     * @return : String
     */
    private static String encrypt(String inputText, String algorithmName) {
        if (inputText == null || "".equals(inputText.trim())) {
            //throw new IllegalArgumentException("Please input content.");
            return "";
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "MD5";
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.toUpperCase().getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptText;
    }

    /**
     * Get String from byte[]
     *
     * @param arr : byte[]
     * @return : String
     */
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}
