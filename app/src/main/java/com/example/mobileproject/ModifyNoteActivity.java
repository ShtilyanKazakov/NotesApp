package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyNoteActivity extends Activity implements View.OnClickListener {

    //Widgets:
    private Button updateNoteBtn, deleteNoteBtn;
    private EditText titleText;
    private EditText descText;
    private DBContext dbContext;
    private long _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Note");
        setContentView(R.layout.activity_modify_note);

        //Instantiation
        titleText = findViewById(R.id.title_edittext);
        descText = findViewById(R.id.description_edittext);
        updateNoteBtn = findViewById(R.id.btn_update);
        deleteNoteBtn = findViewById(R.id.btn_delete);

        dbContext = new DBContext(this);
        dbContext.open();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");

        _id = Long.parseLong(id);

        titleText.setText(title);
        descText.setText(desc);
        updateNoteBtn.setOnClickListener(this);
        deleteNoteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update:
                final String title = titleText.getText().toString();
                final String desc = descText.getText().toString();

                dbContext.update(_id, title, desc);
                this.returnHome();
                break;

            case R.id.btn_delete:
                dbContext.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome(){
        Intent home_intent = new Intent(getApplicationContext(), NoteListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }

}