package com.mindorks.todonotesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract  class NotesDatabse : RoomDatabase(){

    abstract fun notesDao(): NotesDao

    companion object{
        lateinit var INSTANCE: NotesDatabse
        fun getInstance(context: Context): NotesDatabse{
            synchronized(NotesDatabse::class){
                INSTANCE=Room.databaseBuilder(context.applicationContext,NotesDatabse::class.java,"my-notes-db")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE
        }
    }

}
