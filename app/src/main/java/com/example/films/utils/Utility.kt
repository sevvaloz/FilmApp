package com.example.films.utils

class Utility {
    companion object{
        fun isNullOrEmpty(s: String?): Boolean {
            return (s == null) || s.isEmpty()
        }
    }
}