package todolist.utils;

import java.util.UUID;

public class ExceptionMessageUtil {

    public static String generateNotFoundMessage(String type, UUID id) {
        return String.format("%s with id %s not found", type, id);
    }

}
