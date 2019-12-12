package org.erp.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.erp.exception.CustomEmailSigninFailedException;
import org.erp.jwt.JwtTokenProvider;
import org.erp.model.user.UserModel;
import org.erp.model.common.CommonResult;
import org.erp.model.common.SingleResult;
import org.erp.repository.UserRepository;
import org.erp.service.ResponseService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Slf4j
@Api(tags = {"1. Sign"})
@RestController
@RequestMapping("/sign")
@RequiredArgsConstructor
public class SignController {

    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "회원ID : 이메일", required = true)
                               @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true)
                               @RequestParam String password,
                               @ApiParam(value = "이름", required = true)
                               @RequestParam String name,
                               @ApiParam(value = "이메일", required = true)
                               @RequestParam String email
                               ) {

        userRepository.save(UserModel.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인")
    @GetMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true)
                                       @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true)
                                       @RequestParam String password) {

        try {
            UserModel user = userRepository.findByUid(id).orElseThrow(CustomEmailSigninFailedException::new);

            // User의 Password를 암호화했기 때문에 .matches를 사용하여 패스워드를 체크해준다.
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new CustomEmailSigninFailedException();
            }
            return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getMsrl()), user.getRoles()));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃")
    @GetMapping(value = "/signout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.debug("auth : >>>> " + auth);
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        log.debug("auth after: >>>> " + auth);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
