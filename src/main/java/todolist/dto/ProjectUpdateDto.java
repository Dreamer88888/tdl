package todolist.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import todolist.enums.Progress;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProjectUpdateDto {

    @NotNull(message = "Id can't be empty")
    private UUID id;
    private String title;

    @NotNull(message = "Due date can't be empty")
    private long dueAt;

    private String progress;

}
