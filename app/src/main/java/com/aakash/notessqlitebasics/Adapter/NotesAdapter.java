package com.aakash.notessqlitebasics.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.notessqlitebasics.R;
import com.aakash.notessqlitebasics.database.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private Context context;
    private ArrayList<Note> notesList;

    public NotesAdapter(Context context, ArrayList<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_list_row,parent,false);
        return new NotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        final Note note= notesList.get(position);

        holder.tvNote.setText(note.getNote());
        holder.tvTimestamp.setText(formatDate(note.getTimestamp()));

    }

    @Override
    public int getItemCount() {
      return notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView tvNote;
        TextView tvTimestamp;

         NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNote=itemView.findViewById(R.id.note);
            tvTimestamp=itemView.findViewById(R.id.timestamp);
        }
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }


}