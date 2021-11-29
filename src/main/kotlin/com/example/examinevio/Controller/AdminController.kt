package com.example.examinevio.Controller

import com.example.examinevio.Models.*
import com.example.examinevio.Service.GroupService
import com.example.examinevio.Service.SubjectService
import com.example.examinevio.Service.TeacherService
import com.example.examinevio.Service.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AdminController(
    @Autowired
    val groupService: GroupService,
    @Autowired
    val userService: UserService,
    @Autowired
    val subjectService: SubjectService,
    @Autowired
    val teacherService: TeacherService
) {
    @DeleteMapping(value = arrayOf("/admin/subject/{id}"))
    fun delete_subject(@PathVariable id: ObjectId): ResponseEntity<Nothing> {
        return ResponseEntity(null,HttpStatus.OK)
    }
    @GetMapping(value = arrayOf("/admin/groups"))
    fun get_groups(): MutableList<Group> {
        return groupService.getAllGroups()
    }
    @GetMapping(value = arrayOf("/admin/students/on_check"))
    fun get_students(): List<User> {
        return userService.getUnEnabledStudents();
    }
    @GetMapping(value = arrayOf("/admin/teachers/on_check"))
    fun get_teachers_on_check(): List<User> {
        return userService.getUnEnabledTeachers();
    }
    @GetMapping(value = arrayOf("/admin/teachers"))
    fun get_teachers(): MutableList<Teacher> {
        return teacherService.getAllTeachers();
    }
    @GetMapping(value = arrayOf("/admin/subjects"))
    fun get_subjects(): MutableList<Subject> {
        return subjectService.getAllSubjects();
    }
    @PostMapping(value = arrayOf("/admin/group"))
    fun add_group(@RequestBody group: Group): Group {
        return groupService.createGroup(group)
    }
    @PostMapping(value = arrayOf("/admin/subject"))
    fun add_subject(@RequestBody subjectInput: SubjectInput): Subject {
        println(subjectInput)
        return subjectService.createSubject(subjectInput)
    }
    @PostMapping(value = arrayOf("/admin/{name}/student"))
    fun add_student(@PathVariable name: String,@RequestBody student: Student): Group {
        userService.enableUser(student.email)
        return groupService.addStudent(student,name)
    }
    @PostMapping(value = arrayOf("/admin/activate/teacher"))
    fun add_teacher(@RequestBody teacher: Teacher): Teacher {
        userService.enableUser(teacher.email)
        return teacherService.addTeacher(teacher)
    }
    @GetMapping(value = arrayOf("/admin/students/{email}"))
    fun get_student_info(@PathVariable email:String): Student {
        return groupService.findUser(email)
    }
}