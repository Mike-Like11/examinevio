package com.example.examinevio.Config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class MvcConfig : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("index")
        registry.addViewController("/login").setViewName("login")
        registry.addViewController("/registration").setViewName("registration")
        registry.addViewController("/subject/{id}").setViewName("subject")
        registry.addViewController("/subject/{id}/estimate").setViewName("subject_estimation")
        registry.addViewController("/subject/{id}/new_test").setViewName("new_test")
        registry.addViewController("/subject/{id}/test/{id}").setViewName("test")
        registry.addViewController("/subject/{id}/test/{id}/result").setViewName("test_result")
        registry.addViewController("/subject/{id}/test/{id}/edit").setViewName("test_edit")
        registry.addViewController("/my_profile").setViewName("my_profile")
        registry.addViewController("/admin").setViewName("admin")
    }
}