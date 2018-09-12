package com.android.zulfikaranshari.simplenote;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class AllNoteActivity extends AppCompatActivity {
    private RecyclerView mRecycler;
    private DatabaseHelper dbHelper;
    private LinkedList<NoteModel> mModel;
    private NoteAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_all_note);
        setSupportActionBar(toolbar);


        dbHelper = new DatabaseHelper(this);
        mModel = new LinkedList<>();
        mAdapter = new NoteAdapter(this, mModel);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerview);

        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());

        mRecycler.setAdapter(mAdapter);
        showNote();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_new_note){
            startActivity(new Intent(AllNoteActivity.this, InsertNoteActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNote(){
        Cursor cursor = dbHelper.allNote();
        mModel.clear();
        mAdapter.notifyDataSetChanged();
        while (cursor.moveToNext()){
            mModel.add(new NoteModel(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)));
        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
