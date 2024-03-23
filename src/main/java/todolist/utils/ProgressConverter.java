package todolist.utils;

import todolist.enums.Progress;

public class ProgressConverter {

    public static Progress convert(String progress) {
        switch (progress) {
            case "To Do" -> {
                return Progress.TO_DO;
            }
            case "In Progress" -> {
                return Progress.IN_PROGRESS;
            }
            default -> {
                return Progress.DONE;
            }
        }
    }

}
