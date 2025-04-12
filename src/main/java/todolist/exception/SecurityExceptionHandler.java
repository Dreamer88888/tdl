package todolist.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import todolist.config.ResponseProperties;
import todolist.dto.ResponseDto;
import todolist.utils.ResponseDtoUtil;

import java.util.InputMismatchException;

import static todolist.utils.ResponseDtoUtil.generatePayload;

@ControllerAdvice(basePackages = "todolist.controller")
public class SecurityExceptionHandler {

    private final ResponseProperties responseProperties;

    @Autowired
    public SecurityExceptionHandler(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDto<Object>> handleInvalidInput(BadCredentialsException exception) {
        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getAccessDenied().getCode().getIncorrect(),
                responseProperties.getAccessDenied().getMessage().getIncorrect(), generatePayload(exception.getMessage()));

        return ResponseEntity.status(403).body(response);
    }

}
