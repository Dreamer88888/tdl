package todolist.utils;

import todolist.dto.ResponseDto;
import todolist.dto.ResponseSchema;

public class ResponseDtoUtil {

    public static ResponseDto<Object> generateResponse(String code, String message, Object payload) {
        ResponseSchema responseSchema = new ResponseSchema(code, message);

        return new ResponseDto<>(responseSchema, payload);
    }

}
