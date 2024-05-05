package hu.bme.aut.p5c1lf_android_hf.data

import android.content.Context
import androidx.room.*

@Database(entities = [NoteItem::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NoteItemDao(): NoteItemDao

    companion object {
        fun getDatabase(applicationContext: Context): NoteDatabase {
            return Room.databaseBuilder(
                applicationContext,
                NoteDatabase::class.java,
                "note-list"
            ).build();
        }
    }
}
