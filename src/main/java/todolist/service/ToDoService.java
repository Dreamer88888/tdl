package todolist.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.dto.ToDoListRequestDto;
import todolist.dto.ToDoListUpdateDto;
import todolist.entity.Project;
import todolist.entity.ToDoList;
import todolist.enums.Progress;
import todolist.repository.ProjectRepository;
import todolist.repository.ToDoRepository;
import todolist.utils.ExceptionMessageUtil;
import todolist.utils.ProgressConverter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final ProjectRepository projectRepository;

    private static final String type = "To Do List";

    @Autowired
    public ToDoService(ToDoRepository toDoRepository, ProjectRepository projectRepository) {
        this.toDoRepository = toDoRepository;
        this.projectRepository = projectRepository;
    }

    public List<ToDoList> findAll() {
        return toDoRepository.findAll();
    }

    public ToDoList findById(UUID id) {
        Optional<ToDoList> toDoList = toDoRepository.findById(id);

        if (toDoList.isEmpty()) {
            throw new NoSuchElementException(ExceptionMessageUtil.generateNotFoundMessage(type, id));
        }

        return toDoList.get();
    }

    public List<ToDoList> findAllByProjectId(UUID id) {
        return toDoRepository.findAllByProjectId(id);
    }

    public ToDoList add(ToDoListRequestDto toDoListRequestDto) {
        Project project = findProjectById(toDoListRequestDto.getProjectId());

        ToDoList toDoList = ToDoList.builder()
                .project(project)
                .name(toDoListRequestDto.getName())
                .description(toDoListRequestDto.getDescription())
                .progress(Progress.TO_DO)
                .date(System.currentTimeMillis())
                .build();

        return toDoRepository.save(toDoList);
    }

    public ToDoList update(ToDoListUpdateDto toDoListUpdateDto) {
        ToDoList toDoList = ToDoList.builder()
                .id(toDoListUpdateDto.getId())
                .name(toDoListUpdateDto.getName())
                .description(toDoListUpdateDto.getDescription())
                .progress(ProgressConverter.convert(toDoListUpdateDto.getProgress()))
                .date(toDoListUpdateDto.getDate())
                .build();

        return toDoRepository.save(toDoList);
    }

    public void delete(UUID id) {
        toDoRepository.deleteById(id);
    }

    private Project findProjectById(UUID id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty()) {
            throw new NoSuchElementException(String.format("Project with id %s not found", id));
        }

        return project.get();
    }

}
