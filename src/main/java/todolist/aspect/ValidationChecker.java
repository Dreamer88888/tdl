package todolist.aspect;

import jakarta.validation.ValidationException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Aspect
@Component
public class ValidationChecker {

    @Before("execution(* todolist.controller.*.*(..)) && args(.., bindingResult)")
    public void validateBeforeControllerMethodExecution(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();

            int counter = 0;

            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage());

                if (bindingResult.getErrorCount() != 1 || counter < bindingResult.getErrorCount() - 1) {
                    errors.append("; ");
                }
                counter++;
            }

            throw new ValidationException(errors.toString());
        }
    }

}
