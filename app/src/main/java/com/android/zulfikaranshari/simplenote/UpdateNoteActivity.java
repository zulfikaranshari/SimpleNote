package com.android.zulfikaranshari.simplenote;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.LinkedList;

public class UpdateNoteActivity extends AppCompatActivity {
    private EditText mTitle, mContent;
    private DatabaseHelper dbHelper;
    private LinkedList<NoteModel> mModel;
    private NoteModel noteModel;
    private String mID;
    private static final String LOG_TAG = UpdateNoteActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_update_note);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);
        mModel = new LinkedList<>();

        mTitle = (EditText) findViewById(R.id.update_tittle);
        mContent = (EditText) findViewById(R.id.update_content);

        Intent update = getIntent();
        String id = update.getStringExtra("id");
        fillView(id);

//        dbHelper.selectedNote(id);
//        Log.d(LOG_TAG, "id = "+id);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update_note) {
            updateNote(mID);
            return true;
        }
        if (id == R.id.action_delete_note){
            dialogNote();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateNote(String noteId){

        String title = mTitle.getText().toString();
        String content = mContent.getText().toString();
        String id = noteId;
        dbHelper.updateNote(id, title, content);
        Log.d(LOG_TAG, id);
        finish();
        Toast.makeText(UpdateNoteActivity.this, R.string.toast_note_updated, Toast.LENGTH_SHORT).show();


    }

    public void deleteNote(String noteId){
        dbHelper.deleteNote(noteId);
        finish();
        Toast.makeText(UpdateNoteActivity.this, R.string.toast_note_deleted, Toast.LENGTH_SHORT).show();
    }

    public void fillView (String id){
        Cursor cursor = dbHelper.selectedNote(id);
        mModel.clear();

        while (cursor.moveToNext()){
            mModel.add(new NoteModel(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            mID = cursor.getString(0);
            mTitle.setText(cursor.getString(1));
            mContent.setText(cursor.getString(2));
            Log.d(LOG_TAG, mID);
            cursor.moveToFirst();
            cursor.close();
        }

    }

    public void dialogNote(){
        AlertDialog.Builder alert = new AlertDialog.Builder(UpdateNoteActivity.this);
        alert.setMessage(R.string.alert_message);
        alert.setPositiveButton(R.string.alert_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteNote(mID);
            }
        });
        alert.setNegativeButton(R.string.alert_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void exitNote(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Do you want to exit?");
        alert.setMessage("Any unsaved progress will be lost");
        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });
        alert.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateNote(mID);
                finish();
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {
        exitNote();
    }
}
