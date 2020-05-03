package com.mindorks.todonotesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.nfc.Tag
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mindorks.todonotesapp.NotesApp
import com.mindorks.todonotesapp.utils.AppConstant
import com.mindorks.todonotesapp.utils.PrefConstant
import com.mindorks.todonotesapp.R
import com.mindorks.todonotesapp.adapter.NoteAdapter
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener
import com.mindorks.todonotesapp.db.Notes
import com.mindorks.todonotesapp.workmanager.MyWorker
import java.util.*
import java.util.concurrent.TimeUnit

class MyNotes : AppCompatActivity() {
     var fullname: String? =null
    lateinit var fabAddNotes: FloatingActionButton
    val ADD_NOTES_CODE=100

    //    TextView textViewTitle,textViewDescription;
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView
    var notesList = ArrayList<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        setupSharedPreference()
        getDataFromDatabase()
        bindView()
        getIntentData()
        setupRecyclerView()
        setupWorkManager()


        supportActionBar?.title = fullname
        fabAddNotes.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
//                setUpDialogbox()
                val intent = Intent(this@MyNotes,AddNotesActivity::class.java)
                startActivityForResult(intent,ADD_NOTES_CODE)

            }

        })

    }

    private fun setupWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java,1,TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance().enqueue(request)

    }

    private fun getDataFromDatabase() {
        val notesApp =applicationContext as NotesApp
        val notesDao=notesApp.getNotesDb().notesDao()
        notesList.addAll(notesDao.getAll())
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
            val notes = Notes(title=title,description = description)
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {

                notesList.add(notes)
            } else {
                Toast.makeText(this@MyNotes, "Title or Description can't be empty", Toast.LENGTH_SHORT).show()
            }
            addNotesToDb(notes)
            dialog.hide()
        }
        dialog.show()
    }

    private fun addNotesToDb(notes: Notes) {
        val notesApp = applicationContext as NotesApp
        val notesDao =notesApp.getNotesDb().notesDao()
        notesDao.insert(notes)
    }

    private fun setupRecyclerView() {
        val itemClickListener: ItemClickListener = object : ItemClickListener {

            override fun onUpdate(notes: Notes) {


                val notesApp = applicationContext as NotesApp
                val notesDao= notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(notes)
            }


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val  title= data?.getStringExtra(AppConstant.TITLE)
        val  description= data?.getStringExtra(AppConstant.DESCRIPTION)
        val  imagePath= data?.getStringExtra(AppConstant.IMAGE_PATH)
        val notes= Notes(title= title!!,description = description!!,imagePath = imagePath!!, isTaskCompleted = false)
        addNotesToDb(notes)
        notesList.add(notes)
        recyclerViewNotes.adapter?.notifyItemChanged(notesList.size-1)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId==R.id.blogs){
            val intent = Intent(this@MyNotes,BlogsActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}