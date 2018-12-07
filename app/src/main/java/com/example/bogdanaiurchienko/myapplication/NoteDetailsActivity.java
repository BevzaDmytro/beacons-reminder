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

//    HashMap<String, ColorClass> colors = new HashMap<>();
//    {
//        colors.put("color1", new ColorClass( "Honey Suckle", "#fcc875"));
//        colors.put("color2", new ColorClass( "Warm Gray", "#baa896"));
//        colors.put("color3", new ColorClass( "Putty", "#e6ccb5"));
//        colors.put("color4", new ColorClass( "Faded Rose", "#e38b75"));
//        colors.put("color5", new ColorClass( "Linen", "#eae2d6"));
//        colors.put("color6", new ColorClass( "Oyster", "#d5c3aa"));
//        colors.put("color7", new ColorClass( "Biscotti", "#ebb582"));
//        colors.put("color8", new ColorClass( "Hazelnut", "#d6c6b9"));
//    }
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
        setTheBGColor(color);

        //виводимо текст нотатки в поля
        noteNameView.setText(note.getName());
        noteTextView.setText(note.getText());
        noteBeaconsView.setText(note.getBeacons());
    }

    //зберігаємо усі зміни в бд, коли користувач нажимає кнопку "назад"
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("onBackPressed    "+noteNameView.getText().toString() + "   " + noteTextView.getText().toString());
        note.setName(noteNameView.getText().toString());
        note.setText(noteTextView.getText().toString());
    }

    //тут викликаємо актівіті вибору кольору
    public void setColour(View v) {

        final Dialog dialog = new Dialog(NoteDetailsActivity.this);
        dialog.setContentView(R.layout.set_color);

        dialog.setTitle("Choose the colour");

        if(note.getColorId() != -1) {
            RadioGroup radioGroup = dialog.findViewById(R.id.colorRadioGroup);
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(note.getColorId());
            radioButton.setChecked(true);
        }
        //there are a lot of settings, for dialog, check them all out!
        //set up button
        dialog.findViewById(R.id.CancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //set up button
        dialog.findViewById(R.id.OkButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = dialog.findViewById(R.id.colorRadioGroup);
                if (radioGroup!= null && radioGroup.getCheckedRadioButtonId() != -1)
                {
                    int index = radioGroup.getCheckedRadioButtonId();
                    note.setColorId(index);
                    String colorName = ((RadioButton)dialog.findViewById(index)).getText().toString();
                    note.setColor(colors.get(colorName));
                    System.out.println(colorName + "   " + index);
                    color = note.getColor();
                }
                dialog.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it
        dialog.show();
    }

    //тут викликаємо актівіті вибору біконів
    public void setBeacons(View v) {

    }

    //тут видаляємо нотатку і повертаємось на новий екран
    public void deleteNote(View v) {
        
    }

    private void setTheBGColor(String colorCode) {
        //ставимо колір бекграунда той, що записаний у нотатці
        root.setBackgroundColor(Color.parseColor(colorCode));
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.print("on resume!!!!!");
        setTheBGColor(color);
    }
}




class ColorClass{
    String name;
    String code;

    public ColorClass(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorClass color = (ColorClass) o;
        return Objects.equals(code, color.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code);
    }
}