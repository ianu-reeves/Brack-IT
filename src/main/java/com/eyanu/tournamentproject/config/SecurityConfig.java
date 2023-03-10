package com.eyanu.tournamentproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationHandler successHandler;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorizeRequests ->
//            authorizeRequests
//                .antMatchers("/", "authenticate", "/register/**", "*.css", "/tournaments/**").permitAll()
//                .anyRequest().authenticated()
//        )
//        .formLogin( formLogin ->
//            formLogin
//                .loginPage("/login")
//                .loginProcessingUrl("/authenticate")
//                .successHandler(successHandler)
//                .permitAll()
//        )
//        .logout(logout ->
//            logout
//                .logoutSuccessUrl("/login")
//                .permitAll()
//        )
//        .csrf().disable();
            http.authorizeRequests()
                .antMatchers("/", "/authenticate", "/register/**","/*.css", "/tournaments/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .successHandler(successHandler)
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
            .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/static/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(encoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return authenticationProvider;
    }

}
