package todolist.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import todolist.config.ResponseProperties;
import todolist.dto.LoginRequestDto;
import todolist.dto.LoginResponseDto;
import todolist.dto.ResponseDto;
import todolist.entity.ResponseStatus;
import todolist.service.UserService;
import todolist.utils.ResponseDtoUtil;

@RestController
@Slf4j
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final UserService userService;
    private final ResponseProperties responseProperties;

    @Autowired
    public AuthController(UserService userService, ResponseProperties responseProperties) {
        this.userService = userService;
        this.responseProperties = responseProperties;
    }

    @PostMapping("login")
    public ResponseEntity<ResponseDto<Object>> login(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
        LoginResponseDto loginResponse = userService.login(loginRequestDto);

        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getSuccess().getCode().getAuth(),
                responseProperties.getSuccess().getMessage().getAuth(), loginResponse);

        return ResponseEntity.ok(responseDto);
    }

//    @PostMapping("login")
//    public ResponseStatus login(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult) {
//        log.info("username: {}, password: {}", loginRequestDto.getUsername(), loginRequestDto.getPassword());
//        LoginResponseDto loginResponse = userService.login(loginRequestDto);
//
//        ResponseDto<Object> responseDto = ResponseDtoUtil.generateResponse(responseProperties.getSuccess().getCode().getAuth(),
//                responseProperties.getSuccess().getMessage().getAuth(), loginResponse);
//        log.info(responseDto.toString());
//
//        return new ResponseStatus("SUCCESS");
//    }

}
