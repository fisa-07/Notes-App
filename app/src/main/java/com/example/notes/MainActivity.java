package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notes.adapter.CustomAdapter;
import com.example.notes.notesData.Data;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button add_notes;
    private ListView listView;
    private EditText editText;
    private ArrayList<Data> dataArrayList;
    boolean flag;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_notes = findViewById(R.id.add_notes_button);
        listView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.note_editText);

        flag = true;

        loadData();

        add_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() > 0) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        LocalDate date = LocalDate.now();
                        if (flag) {
                            Data data = new Data(editText.getText().toString().trim(), date.toString());
                            if (data != null) {
                                dataArrayList.add(data);
                                saveData();
                                editText.setText("");
                                loadData();
                            }
                        } else {
                            if (editText.getText().toString().trim().length() > 0) {
                                dataArrayList.get(index).setNote(editText.getText().toString().trim());
                                dataArrayList.get(index).setDay(date.toString());
                                saveData();
                                editText.setText("");
                                loadData();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Empty note can't be saved", Toast.LENGTH_SHORT).show();
                            }
                            flag = true;
                        }
                    }
                    }
                    else {
                    Toast.makeText(MainActivity.this, "Empty note can't be saved", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(dataArrayList.get(i).getNote());
                builder.setTitle("Note");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Edit", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    loadData();
                    editText.setText(dataArrayList.get(i).getNote());
                    flag = false;
                    index = i;
                    dialog.cancel();
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("Delete", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dataArrayList.remove(i);
                    saveData();
                    editText.setText("");
                    loadData();
                    dialog.cancel();
                });
                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });
    }

    private void setView() {
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), dataArrayList);
        listView.setAdapter(customAdapter);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.edit().remove("note_list").commit();
        Gson gson = new Gson();
        String json = gson.toJson(dataArrayList);
        editor.putString("note_list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("note_list", null);
        Type type = new TypeToken<ArrayList<Data>>() {
        }.getType();
        dataArrayList = gson.fromJson(json, type);
        if(dataArrayList==null){
            dataArrayList = new ArrayList<>();
        }
        setView();
    }
}