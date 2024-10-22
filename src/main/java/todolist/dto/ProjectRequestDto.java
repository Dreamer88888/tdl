package todolist.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ProjectRequestDto {

    @NotBlank(message = "Title must be filled")
    private String title;

    @NotNull(message = "Due date must be filled")
    private long dueAt;

}
