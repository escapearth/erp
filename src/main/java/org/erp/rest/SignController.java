package org.erp.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.erp.exception.EmailExistsException;
import org.erp.jwt.JwtTokenProvider;
import org.erp.model.common.CommonResult;
import org.erp.model.dto.LoginDTO;
import org.erp.model.user.UserEntity;
import org.erp.repository.UserRepository;
import org.erp.service.ResponseService;
import org.erp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping(value = "api/v1/signup")
    public CommonResult registerUserAccount
            (@ModelAttribute("userEntities") @Valid LoginDTO accountDto,
             BindingResult result,
             WebRequest request,
             Errors errors,
             @ApiParam(value = "회원 Email 계정 : 이메일", required = true)
             @RequestParam String email,
             @ApiParam(value = "비밀번호", required = true)
             @RequestParam String password,
             @ApiParam(value = "이름", required = true)
             @RequestParam String name) {

        log.debug("Registering user account with information: {}", accountDto);

        UserEntity registered = new UserEntity();

        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }

        userRepository.save(UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .build());

        return responseService.getSuccessResult();
    }

//    @ApiOperation(value = "회원가입", notes = "회원가입")
//    @PostMapping(value = "api/v1/signup")
//    public CommonResult signUp(@ApiParam(value = "회원 Email 계정 : 이메일", required = true)
//                               @RequestParam String email,
//                               @ApiParam(value = "비밀번호", required = true)
//                               @RequestParam String password,
//                               @ApiParam(value = "이름", required = true)
//                               @RequestParam String name
//                               ) {
//
//        userRepository.save(UserEntity.builder()
//                .email(email)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .email(email)
//                .roleEntities()
//                .build());
//
//        return responseService.getSuccessResult();
//    }

//    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인")
//    @GetMapping(value = "/signin")
//    public SingleResult<String> signin(@ApiParam(value = "회원 Email 계정 : 이메일", required = true)
//                                       @RequestParam String id,
//                                       @ApiParam(value = "비밀번호", required = true)
//                                       @RequestParam String password) {
//
//        UserEntity userModel = userRepository.findByEmail(id).orElseThrow(CustomEmailSigninFailedException::new);
//       if (!passwordEncoder.matches(password, userModel.getPassword()))
//           throw new CustomEmailSigninFailedException();
//
//       return responseService.getSingleResult(
//               jwtTokenProvider.createToken(userModel.get()
//                       , userModel.getRoleEntities()));
//    }

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

    private UserEntity createUserAccount(LoginDTO accountDto, BindingResult result) {
        UserEntity registered = null;
        try {
            registered = userService.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            e.printStackTrace();
        }
        return registered;
    }
}
