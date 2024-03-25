package todolist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {

    private ResponseSchema responseSchema;
    private T outputSchema;

}
