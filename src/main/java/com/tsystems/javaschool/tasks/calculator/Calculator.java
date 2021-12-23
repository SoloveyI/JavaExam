package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        /* TODO:
            1) Write the input data into an initial array
            2) Validation that the input data is correct
            3) Find an expression inside the parentheses
            4) Write the expression inside the parentheses into a separate array
            5) Calculate the expression inside the separate array
                - Write all the numbers one by one into one list and do the same with the operations ("+", "-", "*", "/")
                - Then go through the list of operations and execute multiplication and division first, and then addition and subtraction.
                - When execute an operation, take the number that matches the operation index and execute the operation on the next number in the list
                - Write the result inside the position of the list of numbers according to the index of the operation
                - Remove the next element from the list of numbers and operation  that was executed
                - Continue going down the list of operations until all operations are executed
            6) Replace the expression in the initial array with the obtained value
            7) Repeat steps 3-5 until there are no expressions in parentheses in the initial array
            8) Calculate the expression in the initial array
            9) Check results for correctness
            10) Editing result for output according to the task
        */

// First validation that "statement" is not empty
        if (statement==null) return null;
        if (statement.equals("")) return null;

// Converting statement to ArrayList and Array

        char[] statement_array=statement.toCharArray();
        ArrayList<String> statement_array_list = new ArrayList<>();
        for (char x:statement_array) {statement_array_list.add(Character.toString(x));}

// Input data validation

        for (char x:statement_array) {if (x!='0'&&x!='1'&&x!='2'&&x!='3'&&x!='4'&&x!='5'&&x!='6'&&x!='7'&&x!='8'&&x!='9'&&x!='('&&x!=')'&&x!='.'&&x!='+'&&x!='-'&&x!='*'&&x!='/') return null;}
        if (statement_array[0]=='+'||statement_array[0]=='-'||statement_array[0]=='*'||statement_array[0]=='/') return null;
        if (statement_array[statement_array.length-1]=='+'||statement_array[statement_array.length-1]=='-'||statement_array[statement_array.length-1]=='*'||statement_array[statement_array.length-1]=='/') return null;

        for (int i=0;i<statement_array_list.size();i++){
            if (i>0) if ((statement_array[i]=='.'||statement_array[i]=='+'||statement_array[i]=='-'||statement_array[i]=='*'||statement_array[i]=='/')&&(statement_array[i-1]=='.'||statement_array[i-1]=='+'||statement_array[i-1]=='-'||statement_array[i-1]=='*'||statement_array[i-1]=='/')) return null;
        }

// Calculation

        int parentheses_count=0; //To work with parentheses

        //Record separation into parenthetical expressions
        int left_parenthesis=0,right_parenthesis;
        for (int i=0;i<statement_array_list.size();i++){

            if (statement_array_list.get(i).equals("(")) {  //Search for left_parenthesis
                if (statement_array_list.get(i+1).equals("+")||statement_array_list.get(i+1).equals("*")||statement_array_list.get(i+1).equals("/")) return null; // Check for validation
                parentheses_count++;
                left_parenthesis=i;
            }
            if (statement_array_list.get(i).equals(")")&parentheses_count%2==0) return null; //Check for parenthesis mistake
            if (statement_array_list.get(i).equals(")")&parentheses_count%2==1) { //Search for right_parenthesis
                if (statement_array_list.get(i-1).equals("+")||statement_array_list.get(i-1).equals("-")||statement_array_list.get(i-1).equals("*")||statement_array_list.get(i-1).equals("/")) return null; // Check for validation
                parentheses_count++;
                right_parenthesis=i;

                /**
                 * When we find the parenthetical expression that does not include another parenthetical expression,
                 * we calculate that expression. Then we replace the result of calculation with elements in statement_array_list ->
                 */

                // Parsing numbers and arithmetic operations inside parenthetical expression

                ArrayList<Float> numbers = new ArrayList<>();                 // List for numbers
                ArrayList<Character> operations = new ArrayList<>();          // List for arithmetic operations
                String buffer="";                                             // Buffer to read numbers

                // Filling in the "numbers" and "operations" lists
                for (int j=left_parenthesis+1;j<right_parenthesis;j++) {
                    char x=statement_array_list.get(j).charAt(0);

                    // If first number <0
                    if (i==0&&x=='-') {
                        buffer += statement_array_list.get(j);
                        continue;}

                    // if number <0
                    if (j>0&&x=='-'&&(statement_array_list.get(j-1).charAt(0)=='+'||statement_array_list.get(j-1).charAt(0)=='-'||statement_array_list.get(j-1).charAt(0)=='*'||statement_array_list.get(j-1).charAt(0)=='/')) {
                        buffer=buffer+statement_array_list.get(j);
                        continue;}

                    // If the element in statement_array_list is not an operation, add it to the buffer
                    if (x!='+'&&x!='-'&&x!='*'&&x!='/') {buffer=buffer+statement_array_list.get(j);}

                    // If the element in statement_array_list is an operation, add it to operations list
                    if ((x=='+'||x=='-'||x=='*'||x=='/')&!buffer.equals("")) {
                        operations.add(x);
                        numbers.add(Float.parseFloat(buffer));
                        buffer="";}

                    // If this is the last iteration, write number from buffer in numbers list
                    if (j==right_parenthesis-1) {
                        numbers.add(Float.parseFloat(buffer));
                        buffer="";}
                }

                // Calculating the value of parenthetical expressions
                try {
                    float number=0;

                    // Multiplication and division are executed first
                    for (int j=0;j< operations.size();j++) {
                        if (operations.get(j)!='+'&&operations.get(j)!='-'){
                            if (operations.get(j)=='*') {
                                number= numbers.get(j) * numbers.get(j+1);
                                // Replace the numbers with value of operation
                                numbers.set(j,number);
                                if (numbers.size()>1) numbers.remove(j+1);
                                if (operations.size()>1) {
                                    operations.remove(j);
                                    j--;
                                }
                            }
                            else if (operations.get(j)=='/') {
                                number= numbers.get(j) / numbers.get(j+1);
                                // Replace the numbers with value of operation
                                numbers.set(j,number);
                                if (numbers.size()>1) numbers.remove(j+1);
                                if (operations.size()>1) {
                                    operations.remove(j);
                                    j--;
                                }
                            }
                        }
                    }

                    // Now do addition and subtraction
                    for (int j=0;j< operations.size();j++) {
                        if (operations.get(j)=='+') {
                            number= numbers.get(j) + numbers.get(j+1);
                            // Replace the numbers with value of operation
                            numbers.set(j,number);
                            if (numbers.size()>1) numbers.remove(j+1);
                            if (operations.size()>1) {
                                operations.remove(j);
                                j--;
                            }
                        }
                        else if (operations.get(j)=='-') {
                            number= numbers.get(j) - numbers.get(j+1);
                            // Replace the numbers with value of operation
                            numbers.set(j,number);
                            if (numbers.size()>1) numbers.remove(j+1);
                            if (operations.size()>1) {
                                operations.remove(j);
                                j--;
                            }
                        }
                    }
                }
                catch (ArithmeticException e){
                    return null;}


                /**
                 * Now numbers.get(0) is value of parenthetical expression. It is time to replace it with elements in statement_array_list.
                 * We remove these elements, then we add value.
                 */

                for (int j=left_parenthesis;j<right_parenthesis+1;j++){statement_array_list.remove(left_parenthesis);}
                statement_array_list.add(left_parenthesis,Float.toString(numbers.get(0)));
                i=-1;
            }
        }
        /**
         * There are no parentheses inside statement now. We need to make some final calculations.
         */

        ArrayList<Float> numbers = new ArrayList<>();                 // List for numbers
        ArrayList<Character> operations = new ArrayList<>();          // List for arithmetic operations
        String buffer="";                                             // Buffer to read numbers

        // Filling in the "numbers" and "operations" lists
        for (int i=0;i<statement_array_list.size();i++) { // Filling in the "numbers" and "operations" lists
            char x=statement_array_list.get(i).charAt(0);

            // If first number <0
            if (i==0&&x=='-') {
                buffer=buffer+statement_array_list.get(i);
                continue;}

            // if number <0
            if (i>0&&x=='-'&&(statement_array_list.get(i-1).charAt(0)=='+'||statement_array_list.get(i-1).charAt(0)=='-'||statement_array_list.get(i-1).charAt(0)=='*'||statement_array_list.get(i-1).charAt(0)=='/')) {
                buffer=buffer+statement_array_list.get(i);
                continue;}

            // If the element in statement_array_list is not an operation, add it to the buffer
            if (x!='+'&&x!='-'&&x!='*'&&x!='/') {buffer=buffer+statement_array_list.get(i);}

            // If the element in statement_array_list is an operation, add it to operations list
            if ((x=='+'||x=='-'||x=='*'||x=='/')&!buffer.equals("")) {
                operations.add(x);
                numbers.add(Float.parseFloat(buffer));
                buffer="";}

            // If this is the last iteration, write number from buffer in numbers list
            if (i==statement_array_list.size()-1) {
                numbers.add(Float.parseFloat(buffer));
                buffer="";}
        }

        // Calculating the value of parenthetical expressions
        try {
            float number=0;

            // Multiplication and division are executed first
            for (int j=0;j< operations.size();j++) {
                if (operations.get(j)!='+'&&operations.get(j)!='-'){
                    if (operations.get(j)=='*') {
                        number= numbers.get(j) * numbers.get(j+1);
                        // Replace the numbers with value of operation
                        numbers.set(j,number);
                        if (numbers.size()>1) numbers.remove(j+1);
                        if (operations.size()>1) {
                            operations.remove(j);
                            j--;
                        }
                    }
                    else if (operations.get(j)=='/') {
                        number= numbers.get(j) / numbers.get(j+1);
                        // Replace the numbers with value of operation
                        numbers.set(j,number);
                        if (numbers.size()>1) numbers.remove(j+1);
                        if (operations.size()>1) {
                            operations.remove(j);
                            j--;
                        }
                    }
                }
            }

            // Now do addition and subtraction
            for (int j=0;j< operations.size();j++) {
                if (operations.get(j)=='+') {
                    number= numbers.get(j) + numbers.get(j+1);
                    // Replace the numbers with value of operation
                    numbers.set(j,number);
                    if (numbers.size()>1) numbers.remove(j+1);
                    if (operations.size()>1) {
                        operations.remove(j);
                        j--;
                    }
                }
                else if (operations.get(j)=='-') {
                    number= numbers.get(j) - numbers.get(j+1);
                    // Replace the numbers with value of operation
                    numbers.set(j,number);
                    if (numbers.size()>1) numbers.remove(j+1);
                    if (operations.size()>1) {
                        operations.remove(j);
                        j--;
                    }
                }
            }
        }
        catch (ArithmeticException e){
            return null;}

        if (numbers.get(0)==Float.POSITIVE_INFINITY||numbers.get(0)==Float.NEGATIVE_INFINITY) return null;  // Validation for infinity

        DecimalFormat result = new DecimalFormat("###.####");
        result.setDecimalSeparatorAlwaysShown(false);
        return String.valueOf(result.format(numbers.get(0))).replace(',','.');
    }

}
