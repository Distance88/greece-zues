package com.zhang.common.core.errors;

import com.zhang.common.core.exception.BizCoreException;
import com.zhang.common.core.exception.ErrorCode;
import com.zhang.common.core.restful.CommonRestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
@Slf4j
public class ExceptionTranslator {

    @ExceptionHandler(ConcurrencyFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorVM processConcurencyError(ConcurrencyFailureException ex) {
        return new ErrorVM(ErrorConstants.ERR_CONCURRENCY_FAILURE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonRestResult processMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String error = CollectionUtils.isEmpty(fieldErrors) ? "参数非法" : fieldErrors.get(0).getDefaultMessage();
        return new CommonRestResult(CommonRestResult.FAIL, error, ErrorCode.SYSTEM_EXCEPTION.getCode());
    }

    @ExceptionHandler(CustomParameterizedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ParameterizedErrorVM processParameterizedValidationError(CustomParameterizedException ex) {
        return ex.getErrorVM();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorVM processAccessDeniedException(AccessDeniedException e) {
        return new ErrorVM(ErrorConstants.ERR_ACCESS_DENIED, e.getMessage());
    }

    private ErrorVM processFieldErrors(List<FieldError> fieldErrors) {
        ErrorVM dto = new ErrorVM(ErrorConstants.ERR_VALIDATION);

        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }

        return dto;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorVM processMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return new ErrorVM(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, exception.getMessage());
    }

    @ExceptionHandler(BizCoreException.class)
    @ResponseBody
    public CommonRestResult processBizCoreException(BizCoreException ex) {
        return new CommonRestResult(CommonRestResult.FAIL, ex.getMessage(), ex.getCode().getCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorVM> processRuntimeException(Exception ex) {
        BodyBuilder builder;
        ErrorVM errorVM;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            builder = ResponseEntity.status(responseStatus.value());
            errorVM = new ErrorVM("error." + responseStatus.value().value(), responseStatus.reason());
        } else {
            builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
            errorVM = new ErrorVM(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, "Internal server error");
        }
        log.error("系统错误", ex);
        return builder.body(errorVM);
    }
}
