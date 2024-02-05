package com.example.natube.model.searchmodel

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)