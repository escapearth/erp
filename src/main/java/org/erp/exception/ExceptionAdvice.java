package org.erp.exception;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.erp.config.MessageConfig;
import org.erp.model.common.CommonResult;
import org.erp.service.ResponseService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CustomMemberNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult memberNotFoundException(HttpServletRequest request, CustomMemberNotFoundException e) {
        return responseService.getFailedResult(Integer.valueOf(getMessage("memberNotFound.code")), getMessage("memberNotFound.msg"));
    }

    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // 현재 Locale 에 맞는 메시지를 조회
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
