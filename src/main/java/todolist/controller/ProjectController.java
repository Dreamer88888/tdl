package todolist.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import todolist.aspect.CheckPermission;
import todolist.dto.ProjectRequestDto;
import todolist.dto.ProjectResponseDto;
import todolist.dto.ProjectUpdateDto;
import todolist.entity.Project;
import todolist.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/project")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<Project>> findAll() {
        return ResponseEntity.ok(projectService.findAllProjects());
    }

    @GetMapping("/{id}")
    @CheckPermission
    public ResponseEntity<ProjectResponseDto> findById(@PathVariable String id) {
        return ResponseEntity.ok(projectService.findProjectById(UUID.fromString(id)));
    }

    @PostMapping
    public ResponseEntity<Project> add(@Valid @RequestBody ProjectRequestDto projectRequestDto, BindingResult bindingResult) {
        return ResponseEntity.status(201).body(projectService.add(projectRequestDto));
    }

    @PutMapping
    public ResponseEntity<Project> update(@Valid @RequestBody ProjectUpdateDto projectUpdateDto, BindingResult bindingResult) {
        return ResponseEntity.ok(projectService.update(projectUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        projectService.delete(UUID.fromString(id));
        return ResponseEntity.ok(String.format("Project with id %s is deleted successfully", id));
    }

}
