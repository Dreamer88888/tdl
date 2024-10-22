package todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private String jwt;
    private UUID userId;
    private String username;
    private String name;
    private List<String> roles;

}
