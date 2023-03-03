package com.example.films.utils

interface RowClickListener<T> {
    fun onRowClick(row: Int, item: T)
}