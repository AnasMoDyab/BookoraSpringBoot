package com.tietoevry.bookorabackend.security;

import com.tietoevry.bookorabackend.security.jwt.AuthTokenFilter;
import com.tietoevry.bookorabackend.security.jwt.AuthenticationEntryPointJWT;
import com.tietoevry.bookorabackend.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration of web security.
 */
@Configuration
@EnableWebSecurity //allows Spring to find and automatically apply the class to the global Web Security
@EnableGlobalMethodSecurity(prePostEnabled = true)//It enables @PreAuthorize, @PostAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationEntryPointJWT authenticationEntryPointJWT;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }


    /**
     * {@inheritDoc}
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Set up the encryption method.
     *
     * @return A PasswordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures DaoAuthenticationProvider by AuthenticationManagerBuilder.userDetailsService()
     *
     * @param authenticationManagerBuilder A AuthenticationManagerBuilder object
     * @throws Exception if user if not found
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    /**
     * Tells Spring Security about configurations about CORS and CSRF, authentication about request, filter to use,
     * time the filter should apply and which Exception Handler is chosen.
     *
     * @param http A HttpSecurity object
     * @throws Exception if any authentication error
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.headers().frameOptions().disable();//TODO for using H2, delete in production


        http.cors().and().csrf().disable()
                //use AuthEntryPointJwt to handle exception
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointJWT).and()
                //Config session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // authorizeRequests that match the following patterns
                .authorizeRequests()
                .antMatchers("/api/v1/employees/**").permitAll() //allow all request from /api/v1/employees/**
                .antMatchers("/api/test/**").permitAll() //allow all request from /api/test/**, but these api are blocked again in controller by e.g. @PreAuthorize("hasRole('ADMIN')")
                .antMatchers("/h2-console/**").permitAll() //TODO for using H2, delete in production
                .antMatchers("/confirm-account").permitAll()
                .antMatchers("/forgot-password").permitAll()
                .antMatchers("/confirm-reset").permitAll()
                .antMatchers("/reset-password").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/v3/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config").permitAll()

                .and()
                //Tell Spring security to use AuthTokenFilter to filter before using UsernamePasswordAuthenticationFilter
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }


}
