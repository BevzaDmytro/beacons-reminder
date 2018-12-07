package com.example.bogdanaiurchienko.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.bogdanaiurchienko.myapplication.model.DataBaseEmulator;
import com.example.bogdanaiurchienko.myapplication.model.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class NoteDetailsActivity extends AppCompatActivity {

    DataBaseEmulator db = DataBaseEmulator.getInstance();
    int noteId;
    Note note;
    View root;
    TextView noteNameView;
    TextView noteTextView;
    TextView noteBeaconsView;
    String color;

    LinkedHashMap<String, String> colors = new LinkedHashMap<>();
    {
        colors.put("Honey Suckle", "#fcc875");
        colors.put("#fcc875", "Honey Suckle");
        colors.put("Warm Gray", "#baa896");
        colors.put("#baa896", "Warm Gray");
        colors.put("Putty", "#e6ccb5");
        colors.put("#e6ccb5", "Putty");
        colors.put("Faded Rose", "#e38b75");
        colors.put("#e38b75", "Faded Rose");
        colors.put("Linen", "#eae2d6");
        colors.put("#eae2d6", "Linen");
        colors.put("Oyster", "#d5c3aa");
        colors.put("#d5c3aa", "Oyster");
        colors.put("Biscotti", "#ebb582");
        colors.put("#ebb582", "Biscotti");
        colors.put("Hazelnut", "#d6c6b9");
        colors.put("#d6c6b9", "Hazelnut");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        //ставимо лісенери на елементи боттом меню
        findViewById(R.id.set_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColour(view);
            }
        });

        findViewById(R.id.set_beacons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBeacons(view);
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote(view);
            }
        });

        //отримуємо поля
        noteNameView = findViewById(R.id.noteName);
        noteTextView = findViewById(R.id.noteText);
        noteBeaconsView = findViewById(R.id.noteBeacons);


        //отримуємо переданий в це актівіті айді нотатки
        noteId = getIntent().getIntExtra("com.example.bogdanaiurchienko.myapplication.NOTE_ID", -1);
        //просимо у бази даних цю нотатку
        note = db.getNote(noteId);
        color = note.getColor();

        //отримуємо батьківський контейнер (щоб поставити його колір, тобто колір бекграунда)
        root = noteNameView.getRootView();
        setTheBGColor();

        //виводимо текст нотатки в поля
        noteNameView.setText(note.getName());
        noteTextView.setText(note.getText());
        noteBeaconsView.setText(note.getBeaconsNames());
    }

    //зберігаємо усі зміни в бд, коли користувач нажимає кнопку "назад"
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("onBackPressed    "+noteNameView.getText().toString() + "   " + noteTextView.getText().toString());
        note.setName(noteNameView.getText().toString());
        note.setText(noteTextView.getText().toString());
    }

    //тут викликаємо діалог вибору кольору
    public void setColour(View v) {

        final Dialog dialog = new Dialog(NoteDetailsActivity.this);
        dialog.setContentView(R.layout.set_color);

        dialog.setTitle("Choose the colour");
        dialog.setCancelable(true);

        View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton)v;
                switch (rb.getId()) {
                    case R.id.color1: color = "#fcc875";
                        break;
                    case R.id.color2: color = "#baa896";
                        break;
                    case R.id.color3: color = "#e6ccb5";
                        break;
                    case R.id.color4: color = "#e38b75";
                        break;
                    case R.id.color5: color = "#eae2d6";
                        break;
                    case R.id.color6: color = "#d5c3aa";
                        break;
                    case R.id.color7: color = "#ebb582";
                        break;
                    case R.id.color8: color = "#d6c6b9";
                        break;
                    default:
                        break;
                }
                note.setColor(color);
            }
        };

        ArrayList <RadioButton> buttons = new ArrayList<>();

        buttons.add((RadioButton) dialog.findViewById(R.id.color1));
        buttons.add((RadioButton) dialog.findViewById(R.id.color2));
        buttons.add((RadioButton) dialog.findViewById(R.id.color3));
        buttons.add((RadioButton) dialog.findViewById(R.id.color4));
        buttons.add((RadioButton) dialog.findViewById(R.id.color5));
        buttons.add((RadioButton) dialog.findViewById(R.id.color6));
        buttons.add((RadioButton) dialog.findViewById(R.id.color7));
        buttons.add((RadioButton) dialog.findViewById(R.id.color8));

        for(RadioButton btn: buttons){
            btn.setOnClickListener(radioButtonClickListener);
            if(Objects.equals(colors.get(btn.getText().toString()), note.getColor())) {
                btn.setChecked(true);
            }
        }

        dialog.findViewById(R.id.OkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheBGColor();
                dialog.dismiss();
            }
        });


        dialog.findViewById(R.id.CancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //тут викликаємо актівіті вибору біконів
    public void setBeacons(View v) {


    }

    //тут видаляємо нотатку і повертаємось на новий екран
    public void deleteNote(View v) {
        db.deleteNote(noteId);
        finish();
    }

    private void setTheBGColor() {
        //ставимо колір бекграунда той, що записаний у нотатці
        root.setBackgroundColor(Color.parseColor(color));
    }

}

