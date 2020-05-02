package com.mindorks.todonotesapp

import android.app.Application
import com.mindorks.todonotesapp.db.NotesDatabse

class NotesApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
    fun getNotesDb():NotesDatabse{
        return NotesDatabse.getInstance(this)
    }
}