package com.example.examinevio.Models

import com.example.examinevio.Models.Test
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("subjects")
data class Subject(
    @Id
    val id:ObjectId = ObjectId.get(),
    val name:String,
    val groups:ArrayList<String>,
    var tests:ArrayList<Test> = ArrayList()
)
