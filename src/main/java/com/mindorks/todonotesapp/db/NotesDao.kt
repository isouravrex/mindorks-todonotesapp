package com.mindorks.todonotesapp.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//Data Access Objects - DAO
@Dao
interface NotesDao{
    @Query("SELECT * from notesData")
    fun getAll():List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun Update(notes: Notes)
    @Delete
    fun delete(notes: Notes)

}