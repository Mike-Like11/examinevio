package com.example.examinevio

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Subject(
    @Id
    val id:ObjectId = ObjectId.get(),
    val name:String,
    val type:String
)
