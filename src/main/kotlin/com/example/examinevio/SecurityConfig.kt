package com.example.examinevio

import com.example.examinevio.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private var userService: UserService
) : WebSecurityConfigurerAdapter() {

    @Autowired
    fun setUserDetailsService( userService: UserService?) {
        this.userService = userService!!
    }
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService(userService)
            .passwordEncoder(BCryptPasswordEncoder())
    }
    override fun configure(http: HttpSecurity?) {
        http!!.csrf()
            .disable()
            .authorizeRequests()
            //Доступ только для не зарегистрированных пользователей
            .antMatchers("/add_user","/registration").not().authenticated()
            //Доступ только для пользователей с ролью Администратор
            //.antMatchers("/admin").hasAuthority("ADMIN")
            .antMatchers("/user_info",).hasAnyAuthority("USER")
            //Доступ разрешен всем пользователей
            .antMatchers("/about/**","/css/**","/js/**","/img/**","/admin/**","/add_user","/registration").permitAll()
            //Все остальные страницы требуют аутентификации
            .anyRequest().authenticated()
            .and()
            //Настройка для входа в систему
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("email")
            .defaultSuccessUrl("/",true)
            .permitAll()
            .and()
            .logout()
            .deleteCookies("dummyCookie")
            .permitAll()
            .logoutSuccessUrl("/login");
    }
}