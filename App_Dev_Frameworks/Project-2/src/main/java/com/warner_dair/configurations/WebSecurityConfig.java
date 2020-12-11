package com.warner_dair.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Old in memory users (can be removed)
    /*
    @Bean
    @Override
    protected UserDetailsService userDetailsService()
    {
        String encodedPassword = passwordEncoder().encode("password");
        UserDetails user = User.withUsername("user").password(encodedPassword).roles("USER").build();
        UserDetails admin = User.withUsername("admin").password(encodedPassword).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/images/**", "/css/**", "/", "/directors", "/director/**", "/films", "/film/**", "/login/**", "/register/**").permitAll()
                .antMatchers("/newdirector", "/newfilm", "/editfilm").hasAnyRole("ADMIN", "USER")
                .antMatchers("/delete/**").hasRole("ADMIN")
                .antMatchers("/api/**", "/myapi/**").hasAnyRole("ADMIN", "API")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/log_me_in")
                .and()
                .logout()
                .logoutUrl("/log_me_out")
                .logoutSuccessUrl("/")
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedPage("/403");

        http.csrf().disable();

    }

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder authBuilder) throws Exception{
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}