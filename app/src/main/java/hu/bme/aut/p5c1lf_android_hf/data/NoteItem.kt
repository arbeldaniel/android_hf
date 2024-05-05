package hu.bme.aut.p5c1lf_android_hf.data

import androidx.room.*

@Entity(tableName = "noteitem")
data class NoteItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "note") var note: String,
    @ColumnInfo(name = "date") var date: String
)
