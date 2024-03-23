package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import todolist.dto.ToDoListRequestDto;
import todolist.dto.ToDoListUpdateDto;
import todolist.entity.ToDoList;
import todolist.service.ToDoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public ResponseEntity<List<ToDoList>> findAll() {
        return ResponseEntity.ok(toDoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoList> findById(@PathVariable String id) {
        return ResponseEntity.ok(toDoService.findById(UUID.fromString(id)));
    }

    @PostMapping
    public ResponseEntity<ToDoList> add(@RequestBody ToDoListRequestDto toDoListRequestDto, BindingResult bindingResult) {
        return ResponseEntity.status(201).body(toDoService.add(toDoListRequestDto));
    }

    @PatchMapping
    public ResponseEntity<ToDoList> update(@RequestBody ToDoListUpdateDto toDoListUpdateDto, BindingResult bindingResult) {
        return ResponseEntity.ok(toDoService.update(toDoListUpdateDto));
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable String id) {
        toDoService.delete(UUID.fromString(id));

        return ResponseEntity.ok(String.format("To Do List with id %s has been deleted.", id));
    }

}
