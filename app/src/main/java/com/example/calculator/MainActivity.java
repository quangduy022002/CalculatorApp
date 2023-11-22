package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;

public class MainActivity extends AppCompatActivity {
    TextView resultTextView,solutionTextView;
    MaterialButton btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    MaterialButton btnDivide,btnMultiply,btnPlus,btnMinus,btnEquals;
    MaterialButton btnC,btnBracketOpen,btnBracketClose;
    MaterialButton btnAC ,btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result);
        solutionTextView = findViewById(R.id.solution);

        // Set up TextWatchers for solutionTv and resultTv
        solutionTextView.addTextChangedListener(textWatcher);
        resultTextView.addTextChangedListener(textWatcher);

        btnC = findViewById(R.id.button_C);
        btnC.setOnClickListener(this::onClick);
        assignBtn(btnBracketOpen,R.id.button_Open_bracket);
        assignBtn(btnBracketClose,R.id.button_Close_bracket);
        assignBtn(btnDivide,R.id.button_Divide);
        assignBtn(btnMultiply,R.id.button_Multiple);
        assignBtn(btnPlus,R.id.button_Plus);
        assignBtn(btnMinus,R.id.button_Minus);
        assignBtn(btnEquals,R.id.button_Equal);
        assignBtn(btn0,R.id.button_0);
        assignBtn(btn1,R.id.button_1);
        assignBtn(btn2,R.id.button_2);
        assignBtn(btn3,R.id.button_3);
        assignBtn(btn4,R.id.button_4);
        assignBtn(btn5,R.id.button_5);
        assignBtn(btn6,R.id.button_6);
        assignBtn(btn7,R.id.button_7);
        assignBtn(btn8,R.id.button_8);
        assignBtn(btn9,R.id.button_9);
        assignBtn(btnAC,R.id.button_AC);
        assignBtn(btnDot,R.id.button_Dot);

        updateButtonC();
    }
    private void updateButtonC() {
        if (solutionTextView.getText().length() < 1 || resultTextView.getText().length() < 1) {
            btnC.setEnabled(false);
        } else {
            btnC.setEnabled(true);
        }
    }

    public void onClick(View view){
        MaterialButton button = (MaterialButton) view;
        String buttonText= button.getText().toString();
        String dataCalculate = solutionTextView.getText().toString();

        if(buttonText.equals("AC")){
            solutionTextView.setText("");
            resultTextView.setText("0");
            return;
        }

        if(buttonText.equals("=")){
            solutionTextView.setText(resultTextView.getText());
            return;
        }

        if(buttonText.equals("C")){
            dataCalculate = dataCalculate.substring(0,dataCalculate.length()-1);
        } else {
            dataCalculate = dataCalculate + buttonText;
        }

        solutionTextView.setText(dataCalculate);
        String finalResult = getResult(dataCalculate);
        if(!finalResult.equals("Having error")){
            resultTextView.setText(finalResult);
        }
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateButtonC();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    void assignBtn(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this::onClick);
    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable script = context.initSafeStandardObjects();
            String result = context.evaluateString(script,data,"Javascript",1,null).toString();
            Object evaluationResult = context.evaluateString(script, data, "Javascript", 1, null);

            if (evaluationResult == Undefined.instance) {
                result = "0";
            }

            if(result.endsWith(".0")){
                result = result.replace(".0", "");
            }
            return result;
        } catch(Exception e){
            return "Having error";
        }
    }
}