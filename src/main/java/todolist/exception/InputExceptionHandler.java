package todolist.exception;

import jakarta.validation.ValidationException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import todolist.config.ResponseProperties;
import todolist.dto.ResponseDto;
import todolist.utils.ResponseDtoUtil;

import java.util.InputMismatchException;

import static todolist.utils.ResponseDtoUtil.generatePayload;

@ControllerAdvice(basePackages = "todolist.controller")
public class InputExceptionHandler {

    private final ResponseProperties responseProperties;

    @Autowired
    public InputExceptionHandler(ResponseProperties responseProperties) {
        this.responseProperties = responseProperties;
    }

    @ExceptionHandler(InputMismatchException.class)
    public ResponseEntity<ResponseDto<Object>> handleInvalidInput(InputMismatchException exception) {
        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getInvalidInput().getCode(),
                responseProperties.getInvalidInput().getMessage(), generatePayload(exception.getMessage()));

        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseDto<Object>> handleMediaNotSupportType(HttpMediaTypeNotSupportedException exception) {
        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getInvalidInput().getCode(),
                responseProperties.getInvalidInput().getMessage(), generatePayload(exception.getMessage()));

        return ResponseEntity.status(404).body(responseDto);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Object>> handleValidationException(ValidationException exception) {
        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getInvalidInput().getCode(),
                responseProperties.getInvalidInput().getMessage(), generatePayload(exception.getMessage()));

        return ResponseEntity.status(400).body(responseDto);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ResponseDto<Object>> handleMaxUploadSize(SizeLimitExceededException exception) {

        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getInvalidInput().getCode(),
                responseProperties.getInvalidInput().getMessage(), generatePayload(exception.getMessage()));

        return ResponseEntity.status(400).body(responseDto);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseDto<Object>> handleMaxUploadSize(MethodArgumentTypeMismatchException exception) {

        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getInvalidInput().getCode(),
                responseProperties.getInvalidInput().getMessage(), generatePayload(exception.getMessage()));

        return ResponseEntity.status(400).body(responseDto);
    }

}
