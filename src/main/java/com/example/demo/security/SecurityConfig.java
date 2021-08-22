package com.example.demo.security;


import com.example.demo.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security.csrf().disable()
                .cors().disable();

        security.authorizeRequests()
                .antMatchers("/api/v1/account/confirm").permitAll()
                .antMatchers("/api/v1/account/list").hasAuthority("ADMIN")

                .antMatchers("/api/v1/order/create").hasAuthority("USER")
                .antMatchers("/api/v1/order/update/**").hasAuthority("SELLER")
                .antMatchers("/api/v1/order/list/seller").hasAuthority("SELLER")
                .antMatchers("/api/v1/order/list/user").hasAuthority("USER")
                .antMatchers("/api/v1/order/reject/**").hasAuthority("SELLER")
                .antMatchers("/api/v1/order/cancel/**").hasAuthority("USER")
                .antMatchers("/api/v1/product/create").hasAuthority("SELLER")
                .antMatchers("/api/v1/product/update/**").hasAuthority("SELLER")
                .antMatchers("/api/v1/product/delete/**").hasAuthority("SELLER")
                .antMatchers("/api/v1/product/all").hasAnyAuthority("USER","SELLER")


                .antMatchers(HttpMethod.POST, "/api/v1/account/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return this.encoder;
    }
}
