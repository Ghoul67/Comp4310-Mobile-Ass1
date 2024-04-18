package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.metrics.BundleSession;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private LinearLayout noteContainer;

    private static final String KEY = "key"; //the key for note by noteAdd

    private static final String KEY_COUNT = "count"; //size of noteList

    private static final String KEY_LIST = "list";

    ArrayList<Note> noteList = new ArrayList<>();
    int size = 0;

    private TextView txtCourseName;
    private TextView txtContent;
    private LinearLayoutCompat noteLayout;

    SharedPreferences sp;

    SharedPreferences.Editor editor;

    SharedPreferences spList;

    private ListView listView;

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        sp = getSharedPreferences(KEY, MODE_PRIVATE);

        spList = getSharedPreferences(KEY_LIST, MODE_PRIVATE);

        editor = spList.edit();



        if(sp.contains(KEY_COUNT)) {
            count = sp.getInt(KEY_COUNT, 0);

            String[] names = new String[count];
            String[] contents = new String[count];
            String[] colors = new String[count];

            for(int i = count-1; i == 0; i--) {
                String json = sp.getString("note" + i, "");
                Gson gson = new Gson();
                Note note = gson.fromJson(json, Note.class);
                noteList.add(note);
                String jsonList = gson.toJson(noteList);
                editor.putString(KEY_LIST, jsonList);
                editor.apply();
                editor.commit();
                size = noteList.size();
            }
            for (int i = count-1; i == 0; i--){
                names[i] = noteList.get(i).getCourse();
                contents[i] = noteList.get(i).getNote();
                colors[i] = noteList.get(i).getColor();
            }

            loadNoteView(names, contents, colors);
        }else{
            size = 0;
        }

        btnAdd = findViewById(R.id.BTNadd);



        btnAction();
    }

    protected void onResume(Bundle savedInstanceState) {
        super.onResume();

        Log.d("tag1", sp.getInt(KEY_COUNT, 0) + "");

        if(sp.contains(KEY_COUNT)) {
            count = sp.getInt(KEY_COUNT, 0);

            String[] names = new String[count];
            String[] contents = new String[count];
            String[] colors = new String[count];

            for(int i = count-1; i == 0; i--) {
                String json = sp.getString("note" + i, "");
                Gson gson = new Gson();
                Note note = gson.fromJson(json, Note.class);
                noteList.add(note);
                String jsonList = gson.toJson(noteList);
                editor.putString(KEY_LIST, jsonList);
                editor.apply();
                editor.commit();
                size = noteList.size();
            }
            for (int i = count-1; i == 0; i--){
                names[i] = noteList.get(i).getCourse();
                contents[i] = noteList.get(i).getNote();
                colors[i] = noteList.get(i).getColor();
            }

            loadNoteView(names, contents, colors);
        }else{
            size = 0;
        }
    }

    private void btnAction(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, noteAdd.class);
                intent.putExtra("size", size);
                startActivity(intent);
            }
        });
    }
    private void loadNoteView(String[] names, String[] contents, String[] colors) {
        listView = findViewById(R.id.ListView);
        CustomBaseAdapter cba = new CustomBaseAdapter(getApplicationContext(), names, contents, colors);
        listView.setAdapter(cba);

    }
}