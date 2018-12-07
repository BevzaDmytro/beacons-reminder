package com.example.bogdanaiurchienko.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public CustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_color_dialog);
        yes = findViewById(R.id.OkButton);
        no = findViewById(R.id.CancelButton);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.OkButton:
                RadioGroup radioGroup = findViewById(R.id.colorRadioGroup);
                String colorName = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                //setTheBGColor(colors.get(colorName));
                break;
            case R.id.CancelButton:
               
                break;
            default:
                break;
        }
        dismiss();
    }
}