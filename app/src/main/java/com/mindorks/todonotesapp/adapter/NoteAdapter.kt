package com.mindorks.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener
import com.mindorks.todonotesapp.db.Notes

class NoteAdapter(val listNotes: List<Notes>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = listNotes[position]
        val title = notes.title
        val description = notes.description
        holder.textViewTitle.text = title
        holder.textViewDescription.text = description

        holder.checkBoxMarkStatus.isChecked = notes.isTaskCompleted
        Glide.with(holder.imageView).load(notes.imagePath).into(holder.imageView)

        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }

        holder.checkBoxMarkStatus.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notes.isTaskCompleted = isChecked

                itemClickListener.onUpdate(notes)
            }

        })
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView= itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView= itemView.findViewById(R.id.textViewDescription)
        val checkBoxMarkStatus: CheckBox= itemView.findViewById(R.id.checkboxMarkStatus)
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
    }

}