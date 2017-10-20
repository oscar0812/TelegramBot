package com.bittle.telegram.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oscartorres on 7/1/17.
 */
public class StringUtils {

    private static String unicodeEscaped(char ch) {
        if (ch < 0x10) {
            return "\\u000" + Integer.toHexString(ch);
        } else if (ch < 0x100) {
            return "\\u00" + Integer.toHexString(ch);
        } else if (ch < 0x1000) {
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }

    public static String StringToUnicode(String str) {
        StringBuilder all = new StringBuilder();
        for (int x = 0; x < str.length(); x++) {
            all.append( unicodeEscaped(str.charAt(x)) );
        }

        return all.toString();
    }

    public static String[] splitString(String str, int maxChars) {
        int rows = (str.length() / maxChars) + 1;
        String[] arr = new String[str.length()];
        if (rows == 1) {
            return (new String[]{str});
        } else {
            int x = 0;
            while (!str.trim().isEmpty()) {
                if (str.trim().length() <= maxChars) {
                    arr[x++] = str;
                    str = "";
                } else {
                    String current = str.substring(0, maxChars);
                    if (str.charAt(maxChars) == ' ') {
                        str = str.substring(maxChars);
                    } else {
                        int space_ind = current.lastIndexOf(' ');
                        if (space_ind > 0) {
                            current = str.substring(0, space_ind);
                            str = str.substring(space_ind);
                        }
                    }
                    arr[x++] = current;
                }
            }

            String[] temp = new String[x];
            for (int y = 0; y < temp.length; y++) {
                temp[y] = arr[y].trim();
            }

            arr = temp;
        }
        return arr;
    }

    public static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean containsEmoji(String str) {
        String regex = "([\\u20a0-\\u32ff\\ud83c\\udc00-\\ud83d\\udeff\\udbb9\\udce5-\\udbb9\\udcee])";

        Matcher matchEmo = Pattern.compile(regex).matcher(str);

        return matchEmo.find();
    }

    static String charToStr(char[] arr) {

        StringBuilder all = new StringBuilder();
        for (char anArr : arr) {
            all.append(anArr);
        }

        return all.toString();
    }

    static boolean startWithNumber(String str) {
        return str.length() >= 1 && str.charAt(0) >= '0' && str.charAt(0) <= '9';
    }
}
