package com.example.examinevio.Controller

import com.example.examinevio.Models.Question
import com.example.examinevio.Models.Subject
import com.example.examinevio.Models.Test
import com.example.examinevio.Service.SubjectService
import com.example.examinevio.Service.TestService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class TestController(
    @Autowired
    private val testService: TestService,
    @Autowired
    private val subjectService: SubjectService
) {
    @PostMapping(value = arrayOf("/subject/{id}/add_test"))
    fun add_test(@PathVariable id:ObjectId,@RequestBody test: Test): String {
        testService.createTest(test)
        subjectService.addTest(id,test)
        return test.id.toString()
    }
    @PostMapping(value = arrayOf("/add_question/{id}"))
    fun add_question(@PathVariable id: ObjectId,@RequestBody question: Question):String{
        testService.addQuestion(id,question)
        return "new_test"
    }
    @GetMapping(value = arrayOf("/tests/{id}"))
    fun test_info(@PathVariable id: ObjectId): Test {
        return testService.getTest(id)
    }
    @GetMapping(value = arrayOf("/subjects/{id}"))
    fun subject_info(@PathVariable id: ObjectId): Subject {
        return subjectService.getSubject(id)
    }
}