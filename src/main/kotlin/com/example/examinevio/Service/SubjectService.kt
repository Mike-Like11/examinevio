package com.example.examinevio.Service

import com.example.examinevio.Models.*
import com.example.examinevio.Repository.GroupRepository
import com.example.examinevio.Repository.SubjectRepository
import com.example.examinevio.Repository.SubjectResultRepository
import com.example.examinevio.Repository.TeacherRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.aggregation.DateOperators
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Service
class SubjectService(
    @Autowired
    val subjectRepository: SubjectRepository,
    @Autowired
    val groupRepository:GroupRepository,
    @Autowired
    val teacherRepository: TeacherRepository,
    @Autowired
    val subjectResultRepository: SubjectResultRepository
) {
    fun createSubject(subjectInput:SubjectInput): Subject {
        val  subject = Subject(name = subjectInput.name)
        subjectInput.teachers.forEach{
           val teacher:Teacher = teacherRepository.findByFio(it)!!
            teacher.subjects.add(subject)
            teacherRepository.save(teacher)
        }
        subjectInput.groups.forEach {
            val group = groupRepository.findByName(it)
            group.subjects.add(subject)
            group.users.forEach {student->
               val subjectResult =  SubjectResult(name = subjectInput.name)
                subjectResultRepository.save(subjectResult)
                student.subjects_results.add(subjectResult)
            }
            groupRepository.save(group)
        }
        return subjectRepository.save(subject)
    }
    fun getSubjects(fio:String): MutableList<Subject> {
        if(teacherRepository.findByFio(fio)!=null){
            return teacherRepository.findByFio(fio)!!.subjects
      }
      else{
            return groupRepository.findGroupByUserName(fio)?.subjects!!
      }
    }
    fun getSubject(id:ObjectId): Subject {
        return subjectRepository.findById(id).get()
    }
    fun getSubjectGroups(id:ObjectId): ArrayList<Group> {
        return groupRepository.findGroupsBySubjects(id)
    }
    fun getSubjectTeachers(id:ObjectId): ArrayList<Teacher> {
        return teacherRepository.findTeacherBySubjects(id)
    }
    fun getAllSubjects(): MutableList<Subject> {
        return subjectRepository.findAll()
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
        val student: Student? = group?.users?.find {
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
                subjectResultRepository.save(subjectResult)
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
        val student: Student? = group?.users?.find {
                student: Student -> student.fio.equals(name)
        }
        val subjectResult: SubjectResult? = student?.subjects_results?.find {
                subjectResult: SubjectResult -> subjectResult.name.equals(subject.name)
        }
        val testResult: TestResult? = subjectResult?.tests_results?.find{
            it.test_name.equals(test.name_test)
        }
        if (testResult!=null){
            if(LocalDateTime.now()<LocalDateTime.parse(testResult.time_start,DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss")).plusMinutes(
                    test.time.toLong()
                )) {
                val questionResult2 = testResult.questions_answers.find {
                    it.id.equals(questionResult.id)
                }
                if (questionResult2 != null) {
                    questionResult2.your_answer = questionResult.your_answer
                }
                println(questionResult2)
                println(testResult)
                subjectResultRepository.save(subjectResult)
            }
            return testResult
        }
       throw NullPointerException()
    }
    fun getSubjectResult(id: ObjectId,name:String): SubjectResult? {
        val subject = subjectRepository.findById(id).get()
        val group = groupRepository.findGroupByUserName(name)
        val student: Student? = group?.users?.find { student: Student ->
            student.fio.equals(name)
        }
        val subjectResult: SubjectResult? = student?.subjects_results?.find { subjectResult: SubjectResult ->
            subjectResult.name.equals(subject.name)
        }
        if (subjectResult!=null) {
            return subjectResult
        }
        else{
           return null
        }
    }
    fun endUserTest(id: ObjectId, test_id: ObjectId, name:String): TestResult {
        val subject = subjectRepository.findById(id).get()
        val test: Test = subject.tests.filter {
            it.id.equals(test_id)
        }.get(0)
        val group = groupRepository.findGroupByUserName(name)
        val student: Student? = group?.users?.find {
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
            subjectResultRepository.save(subjectResult)
            return testResult
        }
        throw NullPointerException()
    }

}