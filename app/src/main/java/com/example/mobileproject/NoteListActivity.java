package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.HashSet;

public class NoteListActivity extends AppCompatActivity {

    private DBContext dbContext;
    private ListView listView;
    private SimpleCursorAdapter adapter;
    protected FloatingActionButton addNoteBtn;

    final String[] from = new String[] {DatabaseHelper.ID,
            DatabaseHelper.TITLE, DatabaseHelper.DESC};

    final int[] to = new int[] {R.id.id, R.id.title, R.id.desc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        dbContext = new DBContext(this);
        dbContext.open();
        Cursor cursor = dbContext.fetch();

        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        addNoteBtn = findViewById(R.id.AddNoteBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(NoteListActivity.this, AddNoteActivity.class);
                startActivity(i);
            }
        });

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewid) {
                TextView idTextView = view.findViewById(R.id.id);
                TextView titleTextView = view.findViewById(R.id.title);
                TextView descTextView = view.findViewById(R.id.desc);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), ModifyNoteActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc",desc);
                modify_intent.putExtra("id",id);

                startActivity(modify_intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                new AlertDialog.Builder(NoteListActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                TextView idTextView = view.findViewById(R.id.id);
                                long id = Long.parseLong(idTextView.getText().toString());

                                dbContext.delete(id);
                                Cursor cursor = dbContext.fetch();
                                adapter.changeCursor(cursor);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }
}