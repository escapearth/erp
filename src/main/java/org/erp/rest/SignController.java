package org.erp.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.erp.exception.CustomEmailSigninFailedException;
import org.erp.exception.EmailExistsException;
import org.erp.jwt.JwtTokenProvider;
import org.erp.model.common.CommonResult;
import org.erp.model.common.SingleResult;
import org.erp.model.dto.LoginDTO;
import org.erp.model.user.UserEntity;
import org.erp.repository.UserRepository;
import org.erp.service.ResponseService;
import org.erp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@Api(tags = {"1. Sign"})
@RestController
@RequiredArgsConstructor
public class SignController {

    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Transactional
    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "api/v1/signup")
    public CommonResult registerUserAccount
            (@ModelAttribute("userEntities") @Valid LoginDTO accountDto,
             BindingResult result
             ) {

        log.debug("Registering user account with information: {}", accountDto);

        UserEntity registered = new UserEntity();

        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }

        return responseService.getSuccessResult();
    }


    /**
    * @author halfdev
    * @since 2020-01-12
    */
    private UserEntity createUserAccount(LoginDTO accountDto, BindingResult result) {
        UserEntity registered = null;
        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        return registered;
    }


    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인")
    @GetMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원 Email 계정 : 이메일", required = true)
                                       @RequestParam String email,
                                       @ApiParam(value = "비밀번호", required = true)
                                       @RequestParam String password) {

        //        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(CustomEmailSigninFailedException::new);
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new CustomEmailSigninFailedException("Email이 올바르지 않습니다.");
        }

       if (!passwordEncoder.matches(password, userEntity.getPassword()))
           throw new CustomEmailSigninFailedException();

       return responseService.getSingleResult(jwtTokenProvider.createToken(userEntity.getId(), userEntity.getRoleEntities()));
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
