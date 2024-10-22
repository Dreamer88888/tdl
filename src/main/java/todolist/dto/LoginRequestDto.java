package todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "Username must be filled")
    private String username;

    @NotBlank(message = "Password must be filled")
    private String password;

}
