package todolist.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import todolist.config.ResponseProperties;
import todolist.dto.ResponseDto;
import todolist.utils.ResponseDtoUtil;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = "todolist.controller")
public class GeneralExceptionHandler {

    private final ResponseProperties responseProperties;

    @Autowired
    public GeneralExceptionHandler(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseDto<Object>> handleResourceNotFound(NoSuchElementException exception) {
        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getNotFound().getCode(),
                responseProperties.getNotFound().getMessage(), exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDto<Object>> handleNullException(NullPointerException exception) {
        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getAccessDenied().getCode().getInvalid(),
                responseProperties.getAccessDenied().getMessage().getInvalid(), ResponseDtoUtil.generatePayload(exception.toString()));

        return ResponseEntity.status(404).body(responseDto);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseDto<Object>> handleIOException(IOException exception) {
        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getNotFound().getCode(),
                responseProperties.getNotFound().getMessage(), ResponseDtoUtil.generatePayload(exception.toString()));

        return ResponseEntity.status(404).body(responseDto);
    }

}
