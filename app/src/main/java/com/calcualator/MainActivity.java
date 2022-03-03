package com.calcualator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText displayText;
    private ArrayList<String> historyCommands = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = findViewById(R.id.textView);

//        this disables the default keyboard from popping up when the text view is selected
        displayText.setShowSoftInputOnFocus(false);

        displayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.display).equals(displayText.getText().toString()))
                    displayText.setText("");
            }
        });
    }

    private void updateText(String textToAdd) {
        String oldText = displayText.getText().toString();
        // grab the cursor position
        int cursorPosition = displayText.getSelectionStart();
        if (getString(R.string.display).equals(displayText.getText().toString()))
            displayText.setText(textToAdd);
        else {
            String textOnLeft = oldText.substring(0, cursorPosition);
            String textOnRight = oldText.substring(cursorPosition);
            // this places the text to add at the position where the cursor is currently placed
            displayText.setText(String.format("%s%s%s", textOnLeft, textToAdd, textOnRight));
        }
        displayText.setSelection(cursorPosition + 1);
    }

    private void replaceText(String text) {
        displayText.setText(text);
        displayText.setSelection(text.length());
    }

    private void clearText() {
        displayText.setText("");
        displayText.setSelection(0);
    }

    public void zeroButton(View view) {
        updateText("0");
    }

    public void oneButton(View view) {
        updateText("1");
    }

    public void twoButton(View view) {
        updateText("2");
    }

    public void threeButton(View view) {
        updateText("3");
    }

    public void fourButton(View view) {
        updateText("4");
    }

    public void fiveButton(View view) {
        updateText("5");
    }

    public void sixButton(View view) {
        updateText("6");
    }

    public void sevenButton(View view) {
        updateText("7");
    }

    public void eightButton(View view) {
        updateText("8");
    }

    public void nineButton(View view) {
        updateText("9");
    }

    public void decimalButton(View view) {
        updateText(".");
    }

    public void plusButton(View view) {
        updateText("+");
    }

    public void minusButton(View view) {
        updateText("-");
    }

    public void multiplyButton(View view) {
        updateText("×");
    }

    public void divideButton(View view) {
        updateText("÷");
    }

    public void exponentButton(View view) {
        updateText("^");
    }

    public void plusMinusButton(View view) {
        updateText("-");
    }

    public void clearButton(View view) {
        displayText.setText("");
    }

    public void paranthesesButton(View view) {
        int cursorPosition = displayText.getSelectionStart();
        int openParanthesesCount = 0;
        int closeParanthesesCount = 0;
        int textLength = displayText.getText().length();

        for (int i = 0; i < cursorPosition; i++) {
            if (displayText.getText().toString().charAt(i) == '(')
                openParanthesesCount++;
            else if (displayText.getText().toString().charAt(i) == ')')
                closeParanthesesCount++;
        }

        if (openParanthesesCount > closeParanthesesCount && displayText.getText().toString().charAt(textLength - 1) != '(')
            updateText(")");
        else if (openParanthesesCount == closeParanthesesCount || displayText.getText().toString().charAt(textLength - 1) == '(')
            updateText("(");
        displayText.setSelection(cursorPosition + 1);
    }

    public void backspaceButton(View view) {
        int cursorPosition = displayText.getSelectionStart();
        int textLength = displayText.getText().length();
        if (cursorPosition > 0 && textLength > 0) {
            String textOnLeft = displayText.getText().toString().substring(0, cursorPosition - 1);
            String textOnRight = displayText.getText().toString().substring(cursorPosition);
            String updatedText = String.format("%s%s", textOnLeft, textOnRight);
            displayText.setText(updatedText);
            displayText.setSelection(cursorPosition - 1);
        }
    }

    public void equalsButton(View view) {
        String userExpression = displayText.getText().toString();

        userExpression = userExpression.replaceAll(getString(R.string.divide), "/");
        userExpression = userExpression.replaceAll(getString(R.string.multiply), "*");

        Expression exp = new Expression(userExpression);
        String result = String.valueOf(exp.calculate());
        
        if (result.equals("NaN")) {
            Toast.makeText(getApplicationContext(), "Invalid operation!", Toast.LENGTH_SHORT).
                    show();
            clearText();
        }
        else {
            historyCommands.add(userExpression);
            displayText.setText(result);
            displayText.setSelection(result.length());
        }
    }

    public void historyButton(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("listOfCommands", historyCommands);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 1) {
                String selectedCommand = data.getStringExtra("selectedCommand");
                replaceText(selectedCommand);
            }
        }
    }
}