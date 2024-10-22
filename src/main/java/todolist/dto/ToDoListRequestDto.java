package todolist.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import todolist.enums.Progress;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ToDoListRequestDto {

    @NotBlank(message = "To Do name can't be empty")
    private String name;
    private String description;

    @NotNull(message = "Date must be filled")
    private long date;

    @NotNull(message = "Project id must be filled")
    private UUID projectId;

}
