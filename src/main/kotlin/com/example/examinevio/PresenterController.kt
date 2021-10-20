package com.example.examinevio

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

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
}