package todolist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Name must be filled")
    private String name;

    @NotBlank(message = "Username must be filled")
    private String username;

    @NotBlank(message = "Password must be filled")
    private String password;

}
