package org.erp.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class SecurityUtilsTest {

    @Test
    public void getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("user", "user"));
        SecurityContextHolder.setContext(securityContext);

        Optional<String> username = SecurityUtils.getCurrentUsername();

        assertThat(username).contains("user");
    }

    @Test
    public void getCurrentUsernameForNoAuthenticationInContext() {
        Optional<String> username = SecurityUtils.getCurrentUsername();

        assertThat(username).isEmpty();
    }
}
