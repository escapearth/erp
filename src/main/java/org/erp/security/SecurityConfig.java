package org.erp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Configure paths and requests that should be ignored by Spring Security
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS)

                // allow anonymous resource requests
                .antMatchers( //
                        "/favicon.ico",
                        "/**/*.css",
                        "/**/*.js",
                        "/h2-console/**", // h2-console 추후 삭제
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger/**"
        );
    }

    // Configure security settings


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // Rest API 이므로 기본설정을 안씀. 기본설정은 비인증시 Login 화면으로 Redirect
                .csrf().disable() // Rest API 이므로 csrf 보안이 필요없음.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Jwt Token이라 Session이 필요없음

                .and()

                .authorizeRequests()
                        .antMatchers(
                                "/*/signin"
                                , "/*/signin/**"
                                , "/*/signup"
                                , "/*/signup/**"
                                , "/social/**")
                .permitAll()

                .and()

                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }
}
