package com.example.bogdanaiurchienko.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ColorDialog extends Dialog {
        // step 1

    Context context;

    public interface OnFooEventListener {
        void fooEvent(String colorName);
    }


    // step 2
    private OnFooEventListener onFooEventListener;

    // Other member field
    // step 3
    public ColorDialog(Context context, OnFooEventListener onFooEventListener)
    {
        super(context);
        this.context = context;
        this.onFooEventListener = onFooEventListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Button btnOk = findViewById(R.id.OkButton);
        btnOk.setOnClickListener( new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // step 4 - to return values from dialog
                RadioGroup radioGroup = findViewById(R.id.colorRadioGroup);
                String colorName = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                onFooEventListener.fooEvent(colorName);
                dismiss();
            }
        });

        Button btnCancel = findViewById(R.id.CancelButton);
        btnCancel.setOnClickListener( new Button.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onFooEventListener.fooEvent("");
                dismiss();
            }
        });
    }
}
