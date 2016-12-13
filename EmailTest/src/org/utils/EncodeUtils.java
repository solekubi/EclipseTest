package org.utils;



import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Encode Utils
 */
public class EncodeUtils {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * Hex encode
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex decode
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * Base64 encode
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * Base64 encode, URL safety(look RFC3548).
     */
    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64 decode
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * Base62 encode
     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * Html encode
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html decode
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Javascript encode
     */
    public static String escapeScript(String input){
        return StringEscapeUtils.escapeEcmaScript(input);
    }

    /**
     * Javascript decode
     */
    public static String unescapeScript(String input){
        return StringEscapeUtils.unescapeEcmaScript(input);
    }

    /**
     * Xml encode
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Xml decode
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL encode Encode default UTF-8.
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * URL decode, Encode default UTF-8.
     */
    public static String urlDecode(String part) {

        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    public static String generateJobId(int length){
        if(length <= 0 || length > 50){
            length = 16;
        }
        return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyz");
    }
}
