package com.hexin.project.rpn.calculator.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author hexin
 * @date 2018/3/3
 */
public class RPNCalculatorTest {

    private RPNCalculator calculator = new RPNCalculator();

    private static void clear(RPNCalculator rpnCalculator) {
        rpnCalculator.execute("clear");
    }

    @Test
    public void inputTest() {
        String input = "5 2";
        calculator.execute(input);
        Assert.assertEquals(input, calculator.resultStack());
        clear(calculator);
    }

    @Test
    public void sqrtTest() {
        String input = "2 sqrt";
        calculator.execute(input);
        Assert.assertEquals("1.4142135624", calculator.resultStack());
        String input1 = "clear 9 sqrt";
        calculator.execute(input1);
        Assert.assertEquals("3", calculator.resultStack());
        clear(calculator);
    }

    @Test
    public void undoTest() {
        String commd1 = "5 4 3 2";
        String expected1 = "5 4 3 2";
        calculator.execute(commd1);
        Assert.assertEquals(expected1, calculator.resultStack());

        String command2 = "undo undo *";
        String expected2 = "20";
        calculator.execute(command2);
        Assert.assertEquals(expected2, calculator.resultStack());

        String commd3 = "5 *";
        String expected3 = "100";
        calculator.execute(commd3);
        Assert.assertEquals(expected3, calculator.resultStack());

        String commd4 = "undo";
        String expected4 = "20 5";
        calculator.execute(commd4);
        Assert.assertEquals(expected4, calculator.resultStack());
        clear(calculator);
    }

    @Test
    public void multiplyAndDivideTest() {
        String commd1 = "7 12 2 /";
        String expected1 = "7 6";
        calculator.execute(commd1);
        Assert.assertEquals(expected1, calculator.resultStack());

        String commd2 = "*";
        String expected2 = "42";
        calculator.execute(commd2);
        Assert.assertEquals(expected2, calculator.resultStack());

        String commd3 = "4 /";
        String expected3 = "10.5";
        calculator.execute(commd3);
        Assert.assertEquals(expected3, calculator.resultStack());
        clear(calculator);
    }

    @Test
    public void example6Test() {
        String commd1 = "1 2 3 4 5";
        String expected1 = "1 2 3 4 5";
        calculator.execute(commd1);
        Assert.assertEquals(expected1, calculator.resultStack());

        String commd2 = "*";
        String expected2 = "1 2 3 20";
        calculator.execute(commd2);
        Assert.assertEquals(expected2, calculator.resultStack());


        String commd3 = "clear 3 4 -";
        String expected3 = "-1";
        calculator.execute(commd3);
        Assert.assertEquals(expected3, calculator.resultStack());
        clear(calculator);
    }

    @Test
    public void test7() {
        String commd1 = "1 2 3 4 5";
        String expected1 = "1 2 3 4 5";
        calculator.execute(commd1);
        Assert.assertEquals(expected1, calculator.resultStack());

        String commd2 = "* * * *";
        String expected2 = "120";
        calculator.execute(commd2);
        Assert.assertEquals(expected2, calculator.resultStack());
        clear(calculator);
    }

}
