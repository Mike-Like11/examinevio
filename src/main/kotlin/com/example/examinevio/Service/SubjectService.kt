package com.example.examinevio.Service

import com.example.examinevio.Models.*
import com.example.examinevio.Repository.GroupRepository
import com.example.examinevio.Repository.SubjectRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Service
class SubjectService(
    @Autowired
    val subjectRepository: SubjectRepository,
    @Autowired
    val groupRepository:GroupRepository
) {
    fun createSubject(subject: Subject): Subject {
        subject.groups.forEach {
            val group = groupRepository.findByName(it)
            group.users.forEach {student->
               student.subjects_results.add(SubjectResult(name = subject.name))
            }
            println(groupRepository.findByName(it))
            groupRepository.save(group)
        }
        return subjectRepository.save(subject)
    }
    fun getSubjects(email:String): MutableList<Subject> {
        return subjectRepository.findSubjects(groupRepository.findGroupByUserName(email).name)
    }
    fun getSubject(id:ObjectId): Subject {
        return subjectRepository.findById(id).get()
    }
    fun addTest(id:ObjectId, test: Test){
        val subject = subjectRepository.findById(id)
        subject.get().tests.add(test)
        subjectRepository.save(subject.get())
    }
    fun addQuestion(sub_id:ObjectId,id:ObjectId, question: Question){
        val subject = subjectRepository.findById(sub_id)
        val test = subject.get().tests.filter {
            it.id.equals(id)
        }
        test.get(0).questions.add(question)
        subjectRepository.save(subject.get())
    }
    fun getTestView(id:ObjectId,test_id:ObjectId,name:String): TestResult {
        val subject = subjectRepository.findById(id).get()
        val test:Test = subject.tests.filter {
            it.id.equals(test_id)
        }.get(0)
        val group = groupRepository.findGroupByUserName(name)
        val student: Student? = group.users.find {
            student: Student -> student.fio.equals(name)
        }
        val subjectResult: SubjectResult? = student?.subjects_results?.find {
                subjectResult: SubjectResult -> subjectResult.name.equals(subject.name)
        }
        val testResult: TestResult? = subjectResult?.tests_results?.find{
            it.test_name.equals(test.name_test)
        }
        if (testResult!=null){
            return testResult
        }
        else{
            val questions:ArrayList<QuestionResult> = ArrayList()
            for (i in 1..test.question_number.toInt()){
                val questions_type = test.questions.filter {
                    it.type.equals(i)
                }
                val question = questions_type.get((0..questions_type.size-1).random())
                questions.add(
                    QuestionResult(question = question,your_answer = "",id = ObjectId.get().toHexString())
                )
            }
            println("testim")
            println(TestResult(test_name = test.name_test, test_time=test.time, result = 0, status = "created", questions_answers =questions))
            if (subjectResult != null) {
                println("yes?")
                subjectResult.tests_results.add(TestResult(
                    test_name = test.name_test,
                    result = 0,
                    test_time=test.time,
                    status = "created",
                    questions_answers =questions))
                groupRepository.save(group)
            }
            return TestResult(test_name = test.name_test, result = 0, test_time=test.time, status = "created", questions_answers =questions)
        }
    }
    fun addAnswerToUserTest(id: ObjectId, test_id: ObjectId, name:String,questionResult: QuestionResult): TestResult {
        val subject = subjectRepository.findById(id).get()
        val test: Test = subject.tests.filter {
            it.id.equals(test_id)
        }.get(0)
        val group = groupRepository.findGroupByUserName(name)
        val student: Student? = group.users.find {
                student: Student -> student.fio.equals(name)
        }
        val subjectResult: SubjectResult? = student?.subjects_results?.find {
                subjectResult: SubjectResult -> subjectResult.name.equals(subject.name)
        }
        val testResult: TestResult? = subjectResult?.tests_results?.find{
            it.test_name.equals(test.name_test)
        }
        if (testResult!=null){
            println(questionResult)
            val questionResult2 = testResult.questions_answers.find {
                it.id.equals(questionResult.id)
            }
            if (questionResult2 != null) {
                questionResult2.your_answer = questionResult.your_answer
            }
            println(questionResult2)
            println(testResult)
            groupRepository.save(group)
            return testResult
        }
       throw NullPointerException()
    }
    fun endUserTest(id: ObjectId, test_id: ObjectId, name:String): TestResult {
        val subject = subjectRepository.findById(id).get()
        val test: Test = subject.tests.filter {
            it.id.equals(test_id)
        }.get(0)
        val group = groupRepository.findGroupByUserName(name)
        val student: Student? = group.users.find {
                student: Student -> student.fio.equals(name)
        }
        val subjectResult: SubjectResult? = student?.subjects_results?.find {
                subjectResult: SubjectResult -> subjectResult.name.equals(subject.name)
        }
        val testResult: TestResult? = subjectResult?.tests_results?.find{
            it.test_name.equals(test.name_test)
        }
        if (testResult!=null){
            testResult.status = "end"
            testResult.result = testResult.questions_answers.sumBy {
                if(it.your_answer.equals(it.question.answer)){
                    1
                }
                else 0
            }
            groupRepository.save(group)
            return testResult
        }
        throw NullPointerException()
    }

}