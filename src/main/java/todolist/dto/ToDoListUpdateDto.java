package todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ToDoListUpdateDto {

    @NotNull(message = "Id can't be empty")
    private UUID id;

    @NotBlank(message = "To Do name can't be empty")
    private String name;
    private String description;
    private String progress;

    @NotNull
    private long date;

}
