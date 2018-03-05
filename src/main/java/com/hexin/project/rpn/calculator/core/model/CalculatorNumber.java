package com.hexin.project.rpn.calculator.core.model;


import com.hexin.project.rpn.calculator.util.NumberUtil;

import java.text.NumberFormat;
import java.util.Objects;


/**
 * @author hexin
 * @date 2018/3/3
 */
public class CalculatorNumber {

    private Number showNum;
    private Number computingNum;

    private NumberFormat showFormat = NumberUtil.createNumberFormatObj(SHOW_NUM_PRECISION);
    private NumberFormat computingFormat = NumberUtil.createNumberFormatObj(COMPUTING_NUM_PRECISION);
    public final static int SHOW_NUM_PRECISION = 10;
    public final static int COMPUTING_NUM_PRECISION = 15;

    /**
     * only refer the computing number, auto address showNum precision.
     */
    public CalculatorNumber(Number computingNum) {
        if (NumberUtil.checkPrecision(computingNum, COMPUTING_NUM_PRECISION)) {
            this.computingNum = Double.parseDouble(computingFormat.format(computingNum));
            this.showNum = Double.parseDouble(showFormat.format(this.computingNum));
        } else {
            this.computingNum = computingNum;
            if (NumberUtil.checkPrecision(computingNum, SHOW_NUM_PRECISION)) {
                this.showNum = Double.parseDouble(showFormat.format(computingNum));
            } else {
                this.showNum = computingNum;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculatorNumber that = (CalculatorNumber) o;
        return Objects.equals(showNum, that.showNum) &&
                Objects.equals(computingNum, that.computingNum);
    }

    @Override
    public int hashCode() {

        return Objects.hash(showNum, computingNum);
    }

    public CalculatorNumber(Number showNum, Number computingNum) {
        this.showNum = showNum;
        this.computingNum = computingNum;
    }

    public Number getShowNum() {
        return showNum;
    }


    public Number getComputingNum() {
        return computingNum;
    }

    public String toString() {
        return showNum.toString();
    }

}
