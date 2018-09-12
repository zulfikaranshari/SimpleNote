package com.android.zulfikaranshari.simplenote;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zulfikaranshari on 04/08/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    private NoteModel mNote;
    private LinkedList<NoteModel>  mModel;
    private NoteModel noteModel;
    private LayoutInflater mInflater;
    private Context mContext;
    private DatabaseHelper dbHelper;
    private static final String LOG_TAG = NoteAdapter.class.getSimpleName();

    public NoteAdapter(Context context, LinkedList<NoteModel> noteModels) {
        this.mModel = noteModels;
        this.mContext = context;
//        mInflater = LayoutInflater.from(context);
    }


    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItem = LayoutInflater.from(mContext).inflate(R.layout.note_list, parent, false);
        return new NoteViewHolder(mContext, mItem);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        mNote = mModel.get(position);
        holder.mTittle.setText(mNote.getTittle());
        holder.mDate.setText(mNote.getDateCreated());
        holder.mTime.setText(mNote.getTimeCreated());
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTittle;
        private TextView mDate;
        private TextView mTime;
        private TextView mId;

//        private TextView mContent;

        public NoteViewHolder(Context context, View itemView) {
            super(itemView);

            mContext = context;

            mTime = (TextView) itemView.findViewById(R.id.time_holder);
            mTittle = (TextView) itemView.findViewById(R.id.tittle_holder);
            mDate = (TextView) itemView.findViewById(R.id.date_holder);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            noteModel =  mModel.get(getAdapterPosition());

            String id  = String.valueOf(noteModel.getId());
            String title = mModel.get(position).toString();

            Intent update = new Intent(mContext, UpdateNoteActivity.class);
            update.putExtra("id", id);
            v.getContext().startActivity(update);
            Log.d(LOG_TAG, id);
        }
    }
}
