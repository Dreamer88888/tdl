package todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.dto.ProjectRequestDto;
import todolist.dto.ProjectResponseDto;
import todolist.dto.ProjectUpdateDto;
import todolist.entity.EmmaUser;
import todolist.entity.Project;
import todolist.enums.Progress;
import todolist.repository.ProjectRepository;
import todolist.utils.ProgressConverter;

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

    public Project add(ProjectRequestDto projectRequestDto) {
        EmmaUser user = new EmmaUser();

        Project project = Project.builder()
                .title(projectRequestDto.getTitle())
                .dueAt(projectRequestDto.getDueAt())
                .user(user)
                .createdAt(System.currentTimeMillis())
                .progress(Progress.TO_DO)
                .build();

        return projectRepository.save(project);
    }

    public Project update(ProjectUpdateDto projectUpdateDto) {
        Project updatedProject = findById(projectUpdateDto.getId());

        updatedProject.setTitle(projectUpdateDto.getTitle());
        updatedProject.setDueAt(projectUpdateDto.getDueAt());
        updatedProject.setProgress(ProgressConverter.convert(projectUpdateDto.getProgress()));

        return projectRepository.save(updatedProject);
    }

    private Project findById(UUID id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty()) {
            throw new NoSuchElementException(String.format("Project with id %s not found", id));
        }

        return project.get();
    }

}
