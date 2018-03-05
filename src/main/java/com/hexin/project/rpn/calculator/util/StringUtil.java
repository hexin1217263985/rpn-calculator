package com.hexin.project.rpn.calculator.util;

import java.util.regex.Pattern;

/**
 * @author hexin
 * @date 2018/3/3
 */
public class StringUtil {

    private final static Pattern WHITE_SPACE_PATTERN = Pattern.compile("\\s+");

    /**
     * split the given string by white space character
     * (including white space character, table character, next line character etc.)
     * into string array.
     *
     * @param str
     * @return the string array splitted from the given str,
     * null is returned if the given str is null or empty.
     */
    public static String[] splitFromWhiteSpace(String str) {
        if (isNullOrEmpty(str))
            return null;
        return WHITE_SPACE_PATTERN.split(str.trim());
    }

    /**
     * validate whether a given str is null
     * or empty (including string only contains invisible characters)
     *
     * @param str the string needs validating
     * @return true if the given string is null or empty, else false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null && str.trim().length() == 0;
    }

    public static void main(String[] args) {
        String[] s1 = splitFromWhiteSpace("hgds  fjdalj \t fdasjlg   fdsajl");
        String[] s2 = splitFromWhiteSpace("hgds  fjdalj \n fdasjlg   fdsajl");
        String[] s3 = splitFromWhiteSpace(" hgds    fjdalj  fdasjlg   fdsajl");
        String[] s4 = splitFromWhiteSpace("hgds  fjdalj  fdasjlg   fdsajl ");
        String[] s5 = splitFromWhiteSpace("hgds  fjdalj  fdasjlg   fdsajl");
        printStrArray(s1);
        printStrArray(s2);
        printStrArray(s3);
        printStrArray(s4);
        printStrArray(s5);

    }

    private static void printStrArray(String[] strings) {
        if (strings != null) {
            for (String s : strings) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }
}
