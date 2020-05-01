package com.mindorks.todonotesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindorks.todonotesapp.R;
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener;
import com.mindorks.todonotesapp.model.Notes;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Notes> listNotes;
    private ItemClickListener itemClickListener;

    public NoteAdapter(List<Notes> list, ItemClickListener itemClickListener){
        this.listNotes=list;
        this.itemClickListener= itemClickListener;

    }


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        final Notes notes = listNotes.get(position);
        String title = notes.getTitle();
        String description = notes.getDescription();
        holder.textViewTitle.setText(title);
        holder.textViewDescription.setText(description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(notes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle, textViewDescription;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription =itemView.findViewById(R.id.textViewDescription);
        }
    }
}
