package com.example.films.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.films.utils.Constants.Companion.filmTable

@Entity (tableName = filmTable)
class Film(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "year") var year: String,
    @ColumnInfo(name = "director") var director: String,
    @ColumnInfo(name = "subject") var subject: String,
    @ColumnInfo(name = "image") var image: String,
): java.io.Serializable {
    @PrimaryKey(autoGenerate = true) var id = 0
}