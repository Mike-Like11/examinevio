package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId

data class QuestionResult (
    val id:String,
    var your_answer:String,
    val question:Question
)