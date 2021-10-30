package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId

data class Question(
    @Id
    val id: ObjectId = ObjectId.get(),
    val desc:String,
    val type:Int,
    val answer:String,
)
