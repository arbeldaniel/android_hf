package hu.bme.aut.p5c1lf_android_hf.data

import androidx.room.*

@Dao
interface NoteItemDao {
    @Query("SELECT * FROM noteitem")
    fun getAll(): List<NoteItem>

    @Insert
    fun insert(shoppingItems: NoteItem): Long

    @Update
    fun update(shoppingItem: NoteItem)

    @Delete
    fun deleteItem(shoppingItem: NoteItem)
}
