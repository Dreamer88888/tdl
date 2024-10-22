package todolist.utils;

import todolist.dto.ResponseDto;
import todolist.dto.ResponseSchema;

import java.util.HashMap;
import java.util.Map;

public class ResponseDtoUtil {

    public static ResponseDto<Object> generateResponse(String code, String message, Object payload) {
        ResponseSchema responseSchema = new ResponseSchema(code, message);

        return new ResponseDto<>(responseSchema, payload);
    }

    public static Map<String, String> generatePayload(String exceptionMessage) {
        Map<String, String> payload = new HashMap<>();

        payload.put("message", exceptionMessage);

        return payload;
    }

}
