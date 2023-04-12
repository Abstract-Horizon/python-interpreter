package org.ah.python.interpreter;

import java.util.Stack;

public class StackCheck {

    public static void main(String[] args) throws Exception {
        Stack<String> stack = new Stack<String>();

        stack.push("1");
        stack.push("2");
        stack.push("3");

        System.out.println("Stack=" + stack);

        stack.push("4");
        stack.push("5");

        System.out.println("Stack=" + stack);

        stack.setSize(3);

        System.out.println("Stack=" + stack);

        String s = stack.pop();

        System.out.println("Popped=" + s);
        System.out.println("Stack=" + stack);
    }
}
