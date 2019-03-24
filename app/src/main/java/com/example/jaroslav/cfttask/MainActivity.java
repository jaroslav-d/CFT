package com.example.jaroslav.cfttask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final String RESULT = "result";

    private Button calcButton;
    private EditText inputText;
    private TextView resultText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText) findViewById(R.id.input);
        calcButton = (Button) findViewById(R.id.button);
        resultText = (TextView) findViewById(R.id.result);

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcButton.setEnabled(false);
                String text = inputText.getText().toString();
                NumericExpression expression = new NumericExpression(text);
                String result = expression.getResult();
                resultText.setText(result);
                calcButton.setEnabled(true);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(RESULT, resultText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resultText.setText(savedInstanceState.getString(RESULT,""));
    }
}
