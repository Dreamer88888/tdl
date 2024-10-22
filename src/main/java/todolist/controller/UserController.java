package todolist.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import todolist.config.ResponseProperties;
import todolist.dto.ResponseDto;
import todolist.dto.UserRequestDto;
import todolist.dto.UserUpdateDto;
import todolist.entity.EmmaUser;
import todolist.service.UserService;
import todolist.utils.ResponseDtoUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final ResponseProperties responseProperties;

    @Autowired
    public UserController(UserService userService, ResponseProperties responseProperties) {
        this.userService = userService;
        this.responseProperties = responseProperties;
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Object>> findAll() {
        List<EmmaUser> users = userService.findAll();

        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getSuccess().getCode().getUser(),
                responseProperties.getSuccess().getMessage().getUser(), users);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDto<Object>> findById(@PathVariable String id) {
        UUID userId = UUID.fromString(id);

        EmmaUser user = userService.findById(userId);

        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getSuccess().getCode().getUser(),
                responseProperties.getSuccess().getMessage().getUser(), user);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Object>> add(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        EmmaUser user = userService.add(userRequestDto);

        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getSuccess().getCode().getUser(),
                responseProperties.getSuccess().getMessage().getUser(), user);

        return ResponseEntity.status(201).body(response);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<Object>> update(@Valid @RequestBody UserUpdateDto userUpdateDto, BindingResult bindingResult) {
        EmmaUser user = userService.update(userUpdateDto);

        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getSuccess().getCode().getUser(),
                responseProperties.getSuccess().getMessage().getUser(), user);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseDto<Object>> delete(@PathVariable String id) {
        UUID userId = UUID.fromString(id);

        userService.delete(userId);

        ResponseDto<Object> response = ResponseDtoUtil.generateResponse(responseProperties.getSuccess().getCode().getUser(),
                responseProperties.getSuccess().getMessage().getUser(), ResponseDtoUtil.generatePayload(String.format("User with id %s successfully deleted", id)));

        return ResponseEntity.ok(response);
    }

}
