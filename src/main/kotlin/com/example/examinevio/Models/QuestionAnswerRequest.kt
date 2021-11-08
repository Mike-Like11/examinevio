package com.example.examinevio.Models

import org.bson.types.ObjectId

data class QuestionAnswerRequest (
    val q_id:ObjectId,
    val your_answer:String)