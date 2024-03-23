package todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.dto.ProjectResponseDto;
import todolist.entity.Project;
import todolist.repository.ProjectRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ToDoService toDoService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ToDoService toDoService) {
        this.projectRepository = projectRepository;
        this.toDoService = toDoService;
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectResponseDto findProjectById(UUID id) {
         return ProjectResponseDto.builder()
                 .project(findById(id))
                 .toDoLists(toDoService.findAllByProjectId(id))
                 .build();
    }

//    public Project add()

    private Project findById(UUID id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty()) {
            throw new NoSuchElementException(String.format("Project with id %s not found", id));
        }

        return project.get();
    }

}
