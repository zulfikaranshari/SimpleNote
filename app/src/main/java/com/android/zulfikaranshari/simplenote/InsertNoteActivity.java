package com.android.zulfikaranshari.simplenote;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InsertNoteActivity extends AppCompatActivity {
    private EditText mTittle, mContent;
    public DatabaseHelper dbHelper;
//    private String mDate, mTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_insert_note);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

        mTittle = (EditText) findViewById(R.id.note_tittle);
        mContent = (EditText) findViewById(R.id.note_content);

    }

    private boolean saveNote(){
        String tittle = mTittle.getText().toString();
        String content = mContent.getText().toString();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");

        String mDate = currentDate.format(date);
        String mTime = currentTime.format(date);

        boolean isInserted = dbHelper.insertNote(tittle, content, mDate, mTime);

        if (isInserted){
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_insert_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_note){
            if (saveNote()){
                startActivity(new Intent(InsertNoteActivity.this, AllNoteActivity.class));
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogNote(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Do you want to exit?");
        alert.setMessage("Any unsaved progress will be lost");
        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(InsertNoteActivity.this, AllNoteActivity.class));
                finish();
            }
        });
        alert.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveNote();
                finish();
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {
        if (!fieldnull()){
            dialogNote();
        }else {
            finish();
        }

    }

    public boolean fieldnull(){
        String title = mTittle.getText().toString();
        String note = mContent.getText().toString();

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(note)){
            return true;
        }else {
            return false;
        }
    }
}
