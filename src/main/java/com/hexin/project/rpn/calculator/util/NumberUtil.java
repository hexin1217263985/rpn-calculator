package com.hexin.project.rpn.calculator.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * @author hexin
 * @date 2018/3/3
 */
public class NumberUtil {

    private final static Pattern DOUBLE_FORMAT = Pattern.compile("(\\d.\\d)+");
    private final static Pattern LONG_FORMAT = Pattern.compile("\\d+");
    private final static Pattern NUMBER_FORMAT = Pattern.compile("\\d+(.(\\d)*)?");


    /**
     * if the given string is number format
     * transform to number object
     * if not return null
     */
    public static Number transFromStr(String num) {
        if (LONG_FORMAT.matcher(num).matches()) {
            return Long.parseLong(num);
        } else if (DOUBLE_FORMAT.matcher(num).matches()) {
            return Double.parseDouble(num);
        } else {
            return null;
        }
    }

    /**
     * check the input string is number
     *
     * @param input
     * @return true for the number format,false for not
     */
    public static boolean isNumber(String input) {
        return NUMBER_FORMAT.matcher(input).matches();
    }

    /**
     * check the given number precision is bigger than the given precision
     *
     * @param number
     * @param precision
     * @return
     */
    public static boolean checkPrecision(Number number, int precision) {
        if (number instanceof Float || number instanceof Double) {
            String numStr = number.toString();
            if (numStr.substring(numStr.indexOf(".")).length() > precision) {
                return true;
            }
        }
        return false;
    }

    public static NumberFormat createNumberFormatObj(int precision) {
        if (precision > 0) {
            StringBuffer sb = new StringBuffer("0.");
            for (int i = 0; i < precision; i++) {
                sb.append("0");
            }
            return new DecimalFormat(sb.toString());
        }
        return null;
    }

}
