package com.itlong.whatsmars.common.util;

/**
 * Created by shenhongxi on 15/5/8.
 */
public class UnicodeUtils {
    public static String native2ascii(String code) {
        char[] chars = code.toCharArray();
        int charValue = 0;
        String result = "";
        for(int i = 0; i < chars.length; i++){
            charValue = (int) code.charAt(i);
            if (charValue <= 256) {
                // result += "& "+Integer.toHexString(charValue)+";";
                result += "\\"+Integer.toHexString(charValue);
            }else{
                // result += "&#x"+Integer.toHexString(charValue)+";";
                result += "\\u"+Integer.toHexString(charValue);
            }
        }
        return result;
    }

    public static String ascii2native(String code) {
        char aChar;
        int len = code.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = code.charAt(x++);
            if (aChar == '\\') {
                aChar = code.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = code.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);

        }
        return outBuffer.toString();
    }

    public static void main(String[] args) {
        String result = native2ascii("中国");
        String _result = ascii2native(result);
        System.out.println(result);
        System.out.println(_result);
    }

}
