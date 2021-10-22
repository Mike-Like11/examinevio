package com.example.examinevio

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class PresenterController {
    @GetMapping(value = arrayOf("/registration"))
    fun registration(): String {
        return "registration";
    }
    @GetMapping(value = arrayOf("/login"))
    fun login(): String {
        return "login";
    }
    @GetMapping(value = arrayOf("/subject"))
    fun subject(): String {
        return "subject";
    }
    @GetMapping(value = arrayOf("/test"))
    fun test(): String {
        return "test";
    }
    @GetMapping(value = arrayOf("/my_profile"))
    fun my_profile(): String {
        return "my_profile";
    }
    @GetMapping(value = arrayOf("/new_test"))
    fun new_test(): String {
        return "new_test";
    }

    @GetMapping(value = arrayOf("/test_edit/{id}"))
    fun test_edit(): String {
        return "test_edit";
    }
}