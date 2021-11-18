package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId

data class SubjectInput (
    @Id
    val id: ObjectId = ObjectId.get(),
    val name:String,
    val groups:ArrayList<String>,
    val teachers:ArrayList<String>
    )