package com.example.examinevio.Controller

import com.example.examinevio.JwtProvider
import com.example.examinevio.Models.*
import com.example.examinevio.Service.GroupService
import com.example.examinevio.Service.SubjectService
import com.example.examinevio.Service.TeacherService
import com.example.examinevio.Service.UserService
import io.swagger.v3.core.util.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
class UserController(
    @Autowired
    val userService: UserService,
    @Autowired
    val subjectService: SubjectService,
    @Autowired
    private val teacherService: TeacherService,
    @Autowired
    private val groupService: GroupService
) {
    @PostMapping(value = arrayOf("/add_user"))
    fun add_user(@RequestBody user: User): User {
        return userService.signUpUser(user)
    }
    @GetMapping(value = arrayOf("/subjects"))
    fun get_subjects(request: HttpServletRequest): ResponseEntity<MutableList<Subject>> {
        if(SecurityContextHolder.getContext().authentication!=null){
            return ResponseEntity(subjectService.getSubjects(SecurityContextHolder.getContext().authentication.name),
                HttpStatus.OK)
        }
        else{
            return ResponseEntity(null,
                HttpStatus.NOT_FOUND)
        }
    }
    @GetMapping(value = arrayOf("/info/teacher"))
    fun get_teacher_info(): Teacher? {
            return teacherService.findTeacher(SecurityContextHolder.getContext().authentication.name)

    }
    @GetMapping(value = arrayOf("/info/role"))
    fun get_role_info(): String {
        return SecurityContextHolder.getContext().authentication.authorities.first().toString()
    }
    @GetMapping(value = arrayOf("/info/student"))
    fun get_student_info(): Student {
        return groupService.findUser(SecurityContextHolder.getContext().authentication.name)
    }
    @GetMapping(value = arrayOf("/subjects_results"))
    fun get_subjects_results(): ResponseEntity<ArrayList<SubjectResult>?> {
        if(SecurityContextHolder.getContext().authentication!=null){
            println(SecurityContextHolder.getContext().authentication.name)
            if(teacherService.findTeacher(SecurityContextHolder.getContext().authentication.name)!=null) {
                println("123456789")
                return ResponseEntity(
                    ArrayList(),
                    HttpStatus.OK
                )
            }
            else{
                return ResponseEntity(
                    groupService.findUser(SecurityContextHolder.getContext().authentication.name).subjects_results,
                    HttpStatus.OK
                )
            }
        }
        else{
            return ResponseEntity(null,
                HttpStatus.NOT_FOUND)
        }
    }
    @PostMapping("/auth")
    fun auth(@RequestBody authInput: AuthInput, httpServletResponse: HttpServletResponse): ResponseEntity<String> {
        val info = userService.findByLoginAndPassword(authInput)
        val cookie = Cookie("token", info.get(0))
        cookie.secure = true
        cookie.isHttpOnly = true
        httpServletResponse.addCookie(cookie)
        return ResponseEntity(info.get(1),HttpStatus.OK)
    }
    @GetMapping("/logout")
    fun auth(httpServletResponse: HttpServletResponse): String {
        val cookie = Cookie("token",null)
        cookie.secure = true
        cookie.isHttpOnly = true
        cookie.maxAge = 0;
        httpServletResponse.addCookie(cookie)
        return ""
    }

}