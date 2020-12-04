package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends Activity implements View.OnClickListener {

    //Widgets:
    private Button addNoteBtn;
    private EditText titleEditText;
    private EditText descEditText;
    private DBContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add Record");
        setContentView(R.layout.activity_add_note);

        //Instantiation
        titleEditText = findViewById(R.id.title_edittext);
        descEditText = findViewById(R.id.description_edittext);
        addNoteBtn = findViewById(R.id.add_record);

        dbContext = new DBContext(this);
        dbContext.open();
        addNoteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_record:
                final String name = titleEditText.getText().toString();
                final String desc = descEditText.getText().toString();

                dbContext.insert(name, desc);

                Intent main = new Intent(AddNoteActivity.this,
                        NoteListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }
}