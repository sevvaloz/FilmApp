package com.example.films.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.films.utils.Constants.Companion.filmTable

@Entity (tableName = filmTable)
data class Film(
    var name: String,
    var year: String,
    var director: String,
    var subject: String,
    var image: String,
): java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1
}