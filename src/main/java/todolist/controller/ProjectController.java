package todolist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.dto.ProjectResponseDto;
import todolist.dto.ResponseDto;
import todolist.entity.Project;
import todolist.service.ProjectService;
import todolist.utils.ResponseDtoUtil;

import java.util.List;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

//    @GetMapping
//    public ResponseEntity<ResponseDto<Object>> findAll() {
//        return ResponseEntity.ok(ResponseDtoUtil.generateResponse());
//    }

}
