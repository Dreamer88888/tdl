package todolist.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@Primary
@Data
@ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "response")
@ComponentScan(basePackages = {"todolist.config"})
public class ResponseProperties {

    private Success success;
    private InvalidInput invalidInput;
    private NotFound notFound;
    private AccessDenied accessDenied;

    @Data
    @AllArgsConstructor
    public static class Success {

        private Code code;
        private Message message;

        @Data
        @AllArgsConstructor
        public static class Code {
            private String project;
            private String todo;
            private String user;
            private String role;
            private String auth;
        }

        @Data
        @AllArgsConstructor
        public static class Message {
            private String project;
            private String todo;
            private String user;
            private String role;
            private String auth;
        }

    }

    @Data
    @AllArgsConstructor
    public static class InvalidInput {
        private String code;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class NotFound {
        private String code;
        private String message;
    }

    @Data
    @AllArgsConstructor
    public static class AccessDenied {
        private Code code;
        private Message message;

        @Data
        @AllArgsConstructor
        public static class Code {
            private String general;
            private String expired;
            private String invalid;
        }

        @Data
        @AllArgsConstructor
        public static class Message {
            private String general;
            private String expired;
            private String invalid;
        }
    }

}
