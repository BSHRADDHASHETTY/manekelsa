package com.example.manekelsa.models

data class Worker(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val skill: String = "",
    val rate: String = "",
    val area: String = "",
    val isAvailable: Boolean = false,
    val rating: Int = 0,
    val imageUrl: String = ""
)