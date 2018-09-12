package com.example.anastacio95.calculator;

import android.widget.EditText;

import java.util.List;
import java.util.Stack;

public class Algorithm extends MainActivity{
    private static boolean isIntDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
	protected static void infixToPostfix(Stack operatorStack, List<String> infix, List<String> postfix){
        //Infix to postfix conversion
        for (int i = 0; i < infix.size(); i++){

            // If current iterator from infix notation is a number, add it to postfix notation
            if (infix.get(i).matches("-?\\d+") || infix.get(i).matches("\\d+\\.\\d+([eE]\\d+)?")){
                postfix.add(infix.get(i));
            }

            // If current iterator from infix notation is + or -, add it to operator stack
            if (infix.get(i).toString().contains("+") || infix.get(i).toString().contains("-")){
                // If operator stack is empty, go ahead and add + or - to operator stack
                if (operatorStack.empty() == true){
                    operatorStack.push(infix.get(i));
                }
                else { // Move the stack operator content to the postfix notation if stack
                      // operator is not empty and if stack operator doesn't contain (
                    while (operatorStack.empty() != true && operatorStack.peek().toString().contains("(") != true){
                        postfix.add(operatorStack.pop().toString());
                    }
                    // Add + or - to operator stack
                    operatorStack.push(infix.get(i));
                }
            }

            // If current iterator from infix notation is * or /, add it to operator stack
            if (infix.get(i).toString().contains("*") || infix.get(i).toString().contains("/")){
                // If operator stack is empty, add * or / to the operator stack
                if (operatorStack.empty() == true){
                    operatorStack.push(infix.get(i));
                }
                else { // Add * or / to the postfix notation if stack
                      // operator contains +, -, or (
                    if (operatorStack.peek().toString().contains("+") || operatorStack.peek().toString().contains("-") || operatorStack.peek().toString().contains("(") == true ){
                        operatorStack.push(infix.get(i));
                    }
                    // Move top operator from operator stack to postix notation
                    // Add * or / to operator stack
                    else {
                        postfix.add(operatorStack.pop().toString());
                        operatorStack.push(infix.get(i));
                    }
                }
            }

            // If current iterator from infix notation is ( add it to operator stack
            if (infix.get(i).toString().contains("(") == true){
                operatorStack.push(infix.get(i));
            }

            // If current iterator from infix notation is ), move the content
            // from operator stack to postfix notation until the top of the
            // operator stack is (
            if (infix.get(i).toString().contains(")") == true){
                if (operatorStack.peek().toString().contains("(") != true){
                    postfix.add(operatorStack.pop().toString());
                }
                //gets rid of the ( from the operator stack
                operatorStack.pop();
            }
        }

        //If operator stack is not empty, empty the rest of the content
        // and add it to postfix notation
        while (operatorStack.empty() != true){
            postfix.add(operatorStack.pop().toString());
        }
    }

    protected static void postfix(Stack postfixStack, List<String> postfix, EditText resultText){
        // Postfix operation algorithm
        for (int j = 0; j < postfix.size(); j++){
            // Push number to the stack
            if (postfix.get(j).matches("-?\\d+") || postfix.get(j).matches("\\d+\\.\\d+([eE]\\d+)?")){
                postfixStack.push(postfix.get(j));
            }

            // Do multiplication operation if the current postfix iterator contains a
            // * sign
            // First number from the top of the postfix stack is used as the right value
            // and second number from the top of the postfix stack is used as the left value
            // The result from the operator is pushed to the postfix stack and the right value
            // and left value are removed from the stack
            if (postfix.get(j).contains("*")){
                double rightNum = Double.parseDouble(postfixStack.pop().toString());
                double leftNum = Double.parseDouble(postfixStack.pop().toString());
                String result = Double.toString(leftNum * rightNum);
                postfixStack.push(result);
            }

            // Do division operation if the current postfix iterator contains a
            // / sign
            // First number from the top of the postfix stack is used as the right value
            // and second number from the top of the postfix stack is used as the left value
            // The result from the operator is pushed to the postfix stack and the right value
            // and left value are removed from the stack
            if (postfix.get(j).contains("/")){
                double rightNum = Double.parseDouble(postfixStack.pop().toString());
                double leftNum = Double.parseDouble(postfixStack.pop().toString());
                String result = Double.toString(leftNum / rightNum);
                postfixStack.push(result);
            }

            // Do addition operation if the current postfix iterator contains a
            // + sign
            // First number from the top of the postfix stack is used as the right value
            // and second number from the top of the postfix stack is used as the left value
            // The result from the operator is pushed to the postfix stack and the right value
            // and left value are removed from the stack
            if (postfix.get(j).contains("+")){
                double rightNum = Double.parseDouble(postfixStack.pop().toString());
                double leftNum = Double.parseDouble(postfixStack.pop().toString());
                String result = Double.toString(leftNum + rightNum);
                postfixStack.push(result);
            }

            // Do subtraction operation if the current postfix iterator contains a
            // - sign
            // First number from the top of the postfix stack is used as the right value
            // and second number from the top of the postfix stack is used as the left value
            // The result from the operator is pushed to the postfix stack and the right value
            // and left value are removed from the stack
            if (postfix.get(j).contains("-")){
                double rightNum = Double.parseDouble(postfixStack.pop().toString());
                double leftNum = Double.parseDouble(postfixStack.pop().toString());
                String result = Double.toString(leftNum - rightNum);
                postfixStack.push(result);
            }
        }
        resultText.setText(postfixStack.pop().toString());
    }
}