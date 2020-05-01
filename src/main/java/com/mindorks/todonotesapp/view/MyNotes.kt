package com.mindorks.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mindorks.todonotesapp.utils.AppConstant
import com.mindorks.todonotesapp.utils.PrefConstant
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.adapter.NoteAdapter
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener
import com.mindorks.todonotesapp.model.Notes
import java.util.*

class MyNotes : AppCompatActivity() {
     var fullname: String? =null
    lateinit var fabAddNotes: FloatingActionButton

    //    TextView textViewTitle,textViewDescription;
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView
    var notesList = ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        setupSharedPreference()
        bindView()
        getIntentData()
        supportActionBar?.title = fullname
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                setUpDialogbox()
            }

        })

    }

    private fun getIntentData() {
        val intent = intent
        fullname = intent.getStringExtra(AppConstant.FULL_NAME)
        if (TextUtils.isEmpty(fullname)) {
            fullname = sharedPreferences.getString(PrefConstant.FULL_NAME, "")!!
        }    }

    private fun setupSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }


    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        //        textViewTitle=findViewById(R.id.textViewTitle);
//        textViewDescription=findViewById(R.id.textViewDescription);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setUpDialogbox() {
        val view = LayoutInflater.from(this@MyNotes).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonsubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonsubmit.setOnClickListener {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            //                textViewTitle.setText(title);
//                textViewDescription.setText(description);
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                val notes = Notes(title,description)
                notesList.add(notes)
            } else {
                Toast.makeText(this@MyNotes, "Title or Description can't be empty", Toast.LENGTH_SHORT).show()
            }
            setupRecyclerView()
            dialog.hide()
        }
        dialog.show()
    }

    private fun setupRecyclerView() {
        val itemClickListener: ItemClickListener = object : ItemClickListener {
            override fun onClick(notes: Notes) {
                val intent = Intent(this@MyNotes, DetailActivity::class.java)
                intent.putExtra(AppConstant.TITLE, notes.title)
                intent.putExtra(AppConstant.DESCRIPTION, notes.description)
                startActivity(intent)
            }
        }
        val noteAdapter = NoteAdapter(notesList, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotes)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = noteAdapter
    }
}