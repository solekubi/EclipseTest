package org.utils;



import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * Digest Utils
 */
public class DigestUtils {

    private static final String SHA1 = "SHA-1";

    private static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    /**
     * Sha1 byte[]
     *
     * @param input : byte[]
     * @return : byte[]
     */
    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    /**
     * Sha1 byte[]
     *
     * @param input : bute[]
     * @param salt  : sha1 , md5
     * @return : byte[]
     */
    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, SHA1, salt, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, SHA1, salt, iterations);
    }

    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * Generate random shat byte[]
     *
     * @param numBytes : byte number
     * @return : byte[]
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * File md5
     *
     * @param input
     * @return
     * @throws java.io.IOException
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * File sha1
     *
     * @param input : file stream
     * @return : byte[]
     * @throws java.io.IOException
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    /**
     * Digest input stream to byte[]
     *
     * @param input     : input stream
     * @param algorithm : algorithm mode
     * @return : byte[]
     * @throws java.io.IOException
     */
    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

}
