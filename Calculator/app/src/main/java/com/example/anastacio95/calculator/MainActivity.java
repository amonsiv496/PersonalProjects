package com.example.anastacio95.calculator;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    //Stack declaration
    private Stack operatorStack = new Stack();
    private Stack postfixStack = new Stack();

    //infix
    private List<String> infix = new ArrayList<>();

    //postfix
    private List<String> postfix = new ArrayList<>();

    String lastNum = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Android layout loaded
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button declarations
        Button zeroBtn = (Button) findViewById(R.id.zeroBtn);
        Button numOneBtn = (Button) findViewById(R.id.numOneBtn);
        Button numTwoBtn = (Button) findViewById(R.id.numTwoBtn);
        Button numThreeBtn = (Button) findViewById(R.id.numThreeBtn);
        Button numFourBtn = (Button) findViewById(R.id.numFourBtn);
        Button numFiveBtn = (Button) findViewById(R.id.numFiveBtn);
        Button numSixBtn = (Button) findViewById(R.id.numSixBtn);
        Button numSevenBtn = (Button) findViewById(R.id.numSevenBtn);
        Button numEightBtn = (Button) findViewById(R.id.numEightBtn);
        Button numNineBtn = (Button) findViewById(R.id.numNineBtn);
        Button plusBtn = (Button) findViewById(R.id.plusBtn);
        Button minusBtn = (Button) findViewById(R.id.minusBtn);
        Button timesBtn = (Button) findViewById(R.id.timesBtn);
        Button divideBtn = (Button) findViewById(R.id.divideBtn);
        Button clearBtn = (Button) findViewById(R.id.clearBtn);
        Button equalsBtn = (Button) findViewById(R.id.equalsBtn);
        Button leftParBtn = (Button) findViewById(R.id.leftParBtn);
        Button rightParBtn = (Button) findViewById(R.id.rightParBtn);

        //Button setOnClickListener
        zeroBtn.setOnClickListener(btnListener);
        numOneBtn.setOnClickListener(btnListener);
        numTwoBtn.setOnClickListener(btnListener);
        numThreeBtn.setOnClickListener(btnListener);
        numFourBtn.setOnClickListener(btnListener);
        numFiveBtn.setOnClickListener(btnListener);
        numSixBtn.setOnClickListener(btnListener);
        numSevenBtn.setOnClickListener(btnListener);
        numEightBtn.setOnClickListener(btnListener);
        numNineBtn.setOnClickListener(btnListener);
        plusBtn.setOnClickListener(btnListener);
        minusBtn.setOnClickListener(btnListener);
        timesBtn.setOnClickListener(btnListener);
        divideBtn.setOnClickListener(btnListener);
        clearBtn.setOnClickListener(btnListener);
        equalsBtn.setOnClickListener(btnListener);
        leftParBtn.setOnClickListener(btnListener);
        rightParBtn.setOnClickListener(btnListener);
    }

    //OnClickListener for buttons
    private OnClickListener btnListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText resultText = (EditText) findViewById(R.id.resultEditText);
            resultText.setGravity(Gravity.BOTTOM);
            Button btns = (Button) v;

            //Gets current working directory
            String filePath = getFilesDir().getPath().toString() + "/nums";
            File file = new File(filePath);
            Scanner input = null;

            String btnString = btns.getText().toString();

            // Number from user appended in text file created
            if (btnString.matches(".*\\d+.*")) {
                try {
                    //append numbers from user to file created
                    FileWriter fw = new FileWriter(file, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw);
                    out.print(btns.getText().toString());
                    out.flush();
                    out.close();

                    //current appended numbers
                    input = new Scanner(file);
                    lastNum = input.nextLine();
                    resultText.append(btnString);
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                input.close();
            }

            // If user enters any operator, operator added to infix notation
            if (btnString.equals("+") || btnString.equals("-")
                || btnString.equals("*") || btnString.equals("/")
                || btnString.equals("(") || btnString.equals(")")){
                try{
                    if (file.exists() == true){
                        input = new Scanner(file);
                        infix.add(input.next());
                        infix.add(btns.getText().toString());
                        input.close();
                        file.delete();
                    }
                    else {
                        infix.add(btns.getText().toString());
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
                resultText.append(btnString);
            }

            // Compute operation when user enters =
            // using from infix to postfix algorithm and execute
            // postfix notation
            if (btnString.contains("=")){
                //Infix created and file deleted
                if (infix.get(infix.size() - 1).equals(")") != true) {
                    infix.add(lastNum);
                }

                file.delete();

                //Infix to postfix conversion
                Algorithm.infixToPostfix(operatorStack, infix, postfix);

                //Postfix algorithm
                Algorithm.postfix(postfixStack, postfix, resultText);

                infix.clear();
                postfix.clear();
                operatorStack.clear();
                postfixStack.clear();
                infix.add(resultText.getText().toString());
                if (file.exists()){
                    file.delete();
                }
            }

            // infix, postfix, operator stack, and postfix stack cleared
            // when user enters CLEAR
            if (btnString.contains("CLEAR")){
                infix.clear();
                postfix.clear();
                operatorStack.clear();
                postfixStack.clear();
                resultText.setText(" ");
                if (file.exists()){
                    file.delete();
                }
            }
        }// end of OnClick
    };// end of OnClickListener
}