package com.hexin.project.rpn.calculator;

import com.hexin.project.rpn.calculator.core.RPNCalculator;

import java.util.Scanner;

/**
 * Main Entrance of the application
 * @author hexin
 * @date 2018/3/3
 */
public class App {
    public static void main(String[] args) {
        System.out.println("RPN Calculator init...");
        System.out.println("Please input command on the console:");
        Scanner scanner = new Scanner(System.in);
        boolean runFlag = true;
        RPNCalculator calculator = new RPNCalculator();
        while (runFlag) {
            if (scanner.hasNext()) {
                String line = scanner.nextLine();
                if ("exit".equalsIgnoreCase(line) || "quit".equalsIgnoreCase(line)) {
                    break;
                }
                try {
                    calculator.execute(line);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
