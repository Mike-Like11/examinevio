package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class TestResult(
    @Id
    val id: ObjectId = ObjectId.get(),
    var status: String,
    val time_start: String = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")),
    var result: Int,
    val test_name:String,
    val test_time: Int,
    val questions_answers: ArrayList<QuestionResult> = ArrayList()
)
