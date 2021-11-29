package com.example.examinevio.Config

import com.example.examinevio.JwtFilter
import com.example.examinevio.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private var userService: UserService,
    @Autowired
    private val jwtFilter: JwtFilter
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

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = Arrays.asList("http://localhost:8080")
        configuration.allowedMethods = Arrays.asList("GET", "POST","OPTIONS","DELETE")
        configuration.allowCredentials = true
        configuration.addAllowedHeader("Authorization")
        configuration.allowedHeaders = Collections.singletonList("*")
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        val source = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source
    }
    override fun configure(http: HttpSecurity?) {
        http!!.httpBasic().disable().cors()
            .and()
            .csrf()
            .disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            //Доступ только для не зарегистрированных пользователей
            .antMatchers("/registration","/login","/add_user").not().authenticated()
            //Доступ только для пользователей с ролью Администратор
            .antMatchers("/admin/**").hasRole("ADMIN")
            //Доступ только для пользователей с ролью Учитель
            .antMatchers("/teacher/*").hasRole("TEACHER")
            //Доступ  для всех пользователей
            .antMatchers("/about/**","/css/**","/js/**","/img/**","/admin/**","/logout","/auth","/","/v3/**","/swagger-ui/**").permitAll()
            .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
            //Доступ к остальным запросам только  для авторизованных пользователей
            .anyRequest().authenticated()
            .and().logout().disable()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java);
    }
}