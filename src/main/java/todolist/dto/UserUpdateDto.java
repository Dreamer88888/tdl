package todolist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserUpdateDto {

    @NotNull(message = "Id must be filled")
    private UUID id;

    @NotBlank(message = "Name must be filled")
    private String name;

}
