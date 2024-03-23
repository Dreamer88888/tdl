package todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.dto.ToDoListRequestDto;
import todolist.dto.ToDoListUpdateDto;
import todolist.entity.ToDoList;
import todolist.enums.Progress;
import todolist.repository.ToDoRepository;
import todolist.utils.ExceptionMessageUtil;
import todolist.utils.ProgressConverter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    private static final String type = "To Do List";

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
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
        ToDoList toDoList = ToDoList.builder()
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

}
