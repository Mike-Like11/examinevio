package com.example.examinevio.Controller

import com.example.examinevio.Models.*
import com.example.examinevio.Service.GroupService
import com.example.examinevio.Service.SubjectService
import com.example.examinevio.Service.TestService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class TestController(
    @Autowired
    private val testService: TestService,
    @Autowired
    private val subjectService: SubjectService,
    @Autowired
    private val groupService: GroupService
) {
    @PostMapping(value = arrayOf("/subject/{id}/add_test"))
    fun add_test(@PathVariable id:ObjectId,@RequestBody test: Test): String {
        testService.createTest(test)
        subjectService.addTest(id,test)
        return test.id.toString()
    }
    @PostMapping(value = arrayOf("/subject/{sub_id}/add_question/{id}"))
    fun add_question(@PathVariable sub_id: ObjectId,@PathVariable id: ObjectId,@RequestBody question: Question):String{
        testService.addQuestion(id,question)
        subjectService.addQuestion(sub_id,id,question)
        return "new_test"
    }
    @PostMapping(value = arrayOf("/subjects/{id}/test/{test_id}/getview/add_answer"))
    fun add_question_answer(@PathVariable test_id: ObjectId, @PathVariable id: ObjectId, @RequestBody answered_question:QuestionResult, principal: Principal): TestResult {
        return subjectService.addAnswerToUserTest(id,test_id,principal.name,questionResult = answered_question)
    }
    @GetMapping(value = arrayOf("/subject/{id}/test/{test_id}/end_test"))
    fun test_info(@PathVariable test_id: ObjectId, @PathVariable id: ObjectId,principal: Principal): TestResult {
        println("yesss")
        return subjectService.endUserTest(id,test_id,principal.name)

    }
    @PostMapping(value = arrayOf("/subjects/{id}/test/testik"))
    fun testikkk(@PathVariable id: ObjectId,@RequestParam value:String):String{
        println(value)
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
    @GetMapping(value = arrayOf("/subjects/{id}/test/{test_id}/getview"))
    fun testview_user(@PathVariable id: ObjectId,@PathVariable test_id: ObjectId,principal: Principal): TestResult {
        return subjectService.getTestView(id,test_id,principal.name)
    }
}