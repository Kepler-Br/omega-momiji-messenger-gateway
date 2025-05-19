package com.momiji.gateway.dto

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val isBot: Boolean,
)
