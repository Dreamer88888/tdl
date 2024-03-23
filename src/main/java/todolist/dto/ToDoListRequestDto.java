package todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import todolist.enums.Progress;

@Data
@AllArgsConstructor
public class ToDoListRequestDto {

    @NotBlank(message = "To Do name can't be empty")
    private String name;
    private String description;

    @NotNull
    private long date;

}
