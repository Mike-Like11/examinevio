package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document("teachers")
data class Teacher(
    @Id
    val id: ObjectId = ObjectId.get(),
    val number:String="",
    val fio:String ="",
    val email:String = "",
    @DBRef
    var subjects:ArrayList<Subject> = ArrayList(),
)
