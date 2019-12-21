package org.erp.security;

import lombok.RequiredArgsConstructor;
import org.erp.jwt.JwtAuthenticationFilter;
import org.erp.jwt.JwtConfigure;
import org.erp.jwt.JwtTokenProvider;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

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
                        "/swagger-ui.html/**",
                        "/webjars/**",
                        "/swagger/**",
                        "/h2-console/**",
                        "/v2/**"
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
                .antMatchers( "/sign/**")
                .permitAll()

                .anyRequest().hasRole("USER") // 그 외 요청은 ROLE_USER 만 가능

                .and()

                .apply(securityConfigurerAdapter())

                .and()

                .formLogin()
                .loginPage("/sign/signin")
                .failureUrl("/login?error")

                .and()

                .logout()
                .logoutUrl("/sign/signout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/sign/signout"))
                .permitAll()

                .and()

                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }

    // Filter Logging
    private JwtConfigure securityConfigurerAdapter() {
        return new JwtConfigure(jwtTokenProvider);
    }

}
