package com.info_board.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    /**
     * The default password string combination is used to convert bytes into hexadecimal characters.
     */
    protected static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            System.err.println(Md5Util.class.getName() + "Initialization failed, MessageDigest does not support MD5Util.");
            nsaex.printStackTrace();
        }
    }

    /**
     * Generate md5 check value of string
     *
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    /**
     * Determine whether the md5 check code of a string matches a known md5 code
     *
     * @param password  The string to be verified
     * @param md5PwdStr md5 check code
     * @return
     */
    public static boolean checkPassword(String password, String md5PwdStr) {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }


    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// Get the digital conversion of the upper 4 bits in the byte, >>>
        // For a logical right shift, the sign bits are shifted to the right together. No difference between the two symbols is found here.
        char c1 = hexDigits[bt & 0xf];// Get digital conversion of the lower 4 bits of the byte
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

}
