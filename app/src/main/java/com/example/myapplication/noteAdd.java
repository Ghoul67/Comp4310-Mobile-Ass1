package com.example.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;

public class noteAdd extends AppCompatActivity {

    private EditText edtCourseName;

    private EditText edtContent;

    private Spinner spnColor;

    private Button btnSave;

    private static final String KEY = "key";
    private static final String KEY_COUNT = "count";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int size = getIntent().getIntExtra("size", 0);

        SharedPreferences sp = getSharedPreferences(KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        edtCourseName = findViewById(R.id.edtCourseName);
        edtContent = findViewById(R.id.edtContent);
        spnColor = findViewById(R.id.spnColor);
        btnSave = findViewById(R.id.btnSave);

        spnColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String color = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "color: "+color, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "default color selected", Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("green");
        colorList.add("beige");
        colorList.add("salamon");
        colorList.add("red");
        colorList.add("purple");
        colorList.add("blue");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnColor.setAdapter(adapter);
        String[] colors = {"#76ABAE", "#F8C471", "#F1948A", "#C0392B", "#9B59B6", "#4752b3"};

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtCourseName.getText().toString().isEmpty() && !edtContent.getText().toString().isEmpty()) {
                    String json = saveNote(colorList, colors);
                    editor.putInt(KEY_COUNT,size);
                    editor.putString(KEY, json);
                    editor.apply();
                    editor.commit();
                    finish();
                }else if(edtCourseName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Course name cannot be empty", Toast.LENGTH_SHORT).show();
                    edtCourseName.setHintTextColor(Color.parseColor("#FF0000"));

                }else if(edtContent.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Note cannot be empty", Toast.LENGTH_SHORT).show();
                    edtContent.setHintTextColor(Color.parseColor("#FF0000"));
                }

            }
        });

    }
   String saveNote(ArrayList<String> colorList, String[] colorCode) {
        String courseName = edtCourseName.getText().toString();
        String content = edtContent.getText().toString();
        String colorName = spnColor.getSelectedItem().toString();
        String color = "#76ABAE";
        for(int i = 0; i < colorList.size(); i++) {
            if(colorList.get(i).equals(colorName)) {
                color = colorCode[i];
            }
        }
        Note note = new Note(courseName, content, color);
        Gson gson = new Gson();
        return gson.toJson(note);

    }

}


