package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document


@Document("subjects_results")
data class SubjectResult(
    @Id
    val id:ObjectId = ObjectId.get(),
    val name:String,
    var mark:Int? = null,
    var extra_points:Int? = null,
    var tests_results:ArrayList<TestResult> = ArrayList()
)
