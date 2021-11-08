package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Field

data class Student (
    @Id
    val id: ObjectId = ObjectId.get(),
    val number:String="",
    val fio:String ="",
    val email:String = "",
    val subjects_results:ArrayList<SubjectResult> = ArrayList()
)