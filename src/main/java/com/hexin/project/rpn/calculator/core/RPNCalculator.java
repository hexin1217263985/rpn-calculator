package com.hexin.project.rpn.calculator.core;

import com.hexin.project.rpn.calculator.core.model.CalculatorNumber;
import com.hexin.project.rpn.calculator.util.NumberUtil;
import com.hexin.project.rpn.calculator.util.StringUtil;

import java.util.Stack;

/**
 * @author hexin
 * @date 2018/3/3
 */
public class RPNCalculator {

    private Stack<CalculatorNumber> numberStack = new Stack<>();
    private Stack<OpRecord> records = new Stack<>();

    //to record the op position
    private int position;


    public void execute(String opStr) {

        String[] ops = StringUtil.splitFromWhiteSpace(opStr);

        if (ops == null) {
            System.err.println("EMPTY INPUT...PLEASE VALID COMMAND");
            return;
        }
        //every op reset position
        position = 0;

        for (String op : ops) {
            position++;
            if (!op(op)) {
                break;
            }
        }
        System.out.println("stack: " + resultStack());

    }


    /**
     * get the result stack info
     */
    public String resultStack() {
        if (numberStack.empty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (CalculatorNumber calculatorNumber : numberStack) {
            sb.append(calculatorNumber).append(" ");
        }
        // remove last whitespace character
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * execute calculator's command
     *
     * @param op
     * @return
     */
    private boolean op(String op) {

        if (NumberUtil.isNumber(op)) {
            if (NumberUtil.checkPrecision(Double.parseDouble(op), CalculatorNumber.SHOW_NUM_PRECISION)) {
                System.err.println("input number's precision is bigger than " + CalculatorNumber.SHOW_NUM_PRECISION + ". please check");
                return false;
            }
            Number number = NumberUtil.transFromStr(op);
            push(number);
            return true;
        }

        switch (op) {

            case "+":
                return binaryCompute(CAL_OP.PLUS);

            case "-":
                return binaryCompute(CAL_OP.MINUS);

            case "*":
                return binaryCompute(CAL_OP.MULTIPLY);

            case "/":
                return binaryCompute(CAL_OP.DIVIDE);

            case "sqrt":
                return unaryCompute(CAL_OP.SQRT);

            case "undo":
                if (!undo()) {
                    System.err.println("there is no op before undo");
                    return false;
                }
                return true;

            case "clear":
                numberStack.clear();
                records.clear();
                return true;

            default:
                System.err.println("unknown command----" + op);
                return false;
        }
    }


    /**
     * for basic calculation
     * including binary calculation -,+,*,/
     *
     * @param op
     * @return
     */
    private boolean binaryCompute(CAL_OP op) {

        if (checkSize(2)) {
            printInsucientParaError(op.value());
            return false;
        }

        CalculatorNumber lastNumber = numberStack.pop();
        CalculatorNumber firstNumber = numberStack.pop();
        Number last = lastNumber.getComputingNum();
        Number first = firstNumber.getComputingNum();
        Number result;
        switch (op) {
            case PLUS:
                result = last.doubleValue() + first.doubleValue();
                break;
            case MINUS:
                result = first.doubleValue() - last.doubleValue();
                break;
            case MULTIPLY:
                result = first.doubleValue() * last.doubleValue();
                break;
            case DIVIDE:
                if (last.doubleValue() == 0) {
                    System.err.println("the divide number is 0, please check.");
                    numberStack.push(firstNumber);
                    numberStack.push(lastNumber);
                    return false;
                }
                result = first.doubleValue() / last.doubleValue();
                break;
            default:
                numberStack.push(firstNumber);
                numberStack.push(lastNumber);
                return false;
        }
        pushComputedResult(result);
        records.push(new OpRecord(op, firstNumber, lastNumber));
        return true;
    }

    private void pushComputedResult(Number result) {
        String resultStr = result.toString();
        if (resultStr.endsWith(".0")) {
            Number longNumber = Long.parseLong(resultStr.substring(0, resultStr.length() - 2));
            numberStack.push(new CalculatorNumber(longNumber, longNumber));
        } else {
            numberStack.push(new CalculatorNumber(result));
        }
    }

    private boolean unaryCompute(CAL_OP op) {
        if (checkSize(1)) {
            printInsucientParaError(op.value());
            return false;
        }
        CalculatorNumber numer = numberStack.pop();
        Number calNum = numer.getComputingNum();
        Number result;
        switch (op) {
            case SQRT:
                if (calNum.doubleValue() < 0) {
                    System.err.println("the inputting number executing SQRT function should above 0, please check.");
                    numberStack.push(numer);
                    return false;
                }
                result = Math.sqrt(calNum.doubleValue());
                break;
            default:
                numberStack.push(numer);
                return false;
        }
        pushComputedResult(result);
        records.push(new OpRecord(op, numer, null));
        return true;
    }

    private void printInsucientParaError(String op) {
        System.err.printf("operator%s(position:%d):insucient parameters", op, position);
        System.out.println();
    }

    private boolean checkSize(int size) {
        return numberStack.size() < size;
    }


    private void push(Number number) {
        CalculatorNumber calculatorNumber = new CalculatorNumber(number, number);
        numberStack.push(calculatorNumber);
        records.push(new OpRecord(CAL_OP.INPUT, calculatorNumber, null));
    }

    private boolean undo() {
        if (!records.empty()) {
            OpRecord record = records.pop();
            undoOp(record, numberStack);
            return true;
        }
        return false;
    }

    /**
     * concrete undo operation
     */
    private void undoOp(OpRecord record, Stack<CalculatorNumber> undoStack) {
        switch (record.getOp()) {
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVIDE:
                undoStack.pop();
                undoStack.push(record.getFirst());
                undoStack.push(record.getLast());
                break;
            case SQRT:
                undoStack.pop();
                undoStack.push(record.getFirst());
                break;
            case INPUT:
                undoStack.pop();
                break;
            default:
                break;
        }
    }

    /**
     * the class to record every op record
     */
    private static class OpRecord {
        private CAL_OP op;
        //the first op number if is not input type
        private CalculatorNumber first;
        //the last if op is not sqrt or input type
        private CalculatorNumber last;

        public OpRecord(CAL_OP op, CalculatorNumber first, CalculatorNumber last) {
            this.op = op;
            this.first = first;
            this.last = last;
        }

        public CalculatorNumber getFirst() {
            return first;
        }

        public CalculatorNumber getLast() {
            return last;
        }

        public CAL_OP getOp() {
            return op;
        }
    }

    enum CAL_OP {
        PLUS("+", 2), MINUS("-", 2), MULTIPLY("*", 2), DIVIDE("/", 2), SQRT("sqrt", 1), INPUT("input", 0);

        String opString;
        int opNumSize;

        CAL_OP(String opString, int opNumSize) {
            this.opString = opString;
            this.opNumSize = opNumSize;
        }

        public String value() {
            return this.opString;
        }

        public int size() {
            return this.opNumSize;
        }
    }
}
