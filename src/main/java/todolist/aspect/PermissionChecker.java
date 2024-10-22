package todolist.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import todolist.dto.ProjectResponseDto;
import todolist.service.ProjectService;
import todolist.utils.AuthUtil;

import java.util.UUID;

@Aspect
@Slf4j
@Component
public class PermissionChecker {

    private final ProjectService projectService;

    @Autowired
    public PermissionChecker(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Pointcut("@annotation(todolist.aspect.CheckPermission)")
    public void checkPermissionAnnotation() {}

    @Before("checkPermissionAnnotation() && @annotation(checkPermission)")
    public void checkPermission(JoinPoint joinPoint, CheckPermission checkPermission) {
        UUID userId = AuthUtil.getUserId();
        Object[] signatureArgs = joinPoint.getArgs();

        UUID projectId = UUID.fromString(String.valueOf(signatureArgs[0]));

        ProjectResponseDto project = projectService.findProjectById(projectId);

        if (!isEligible(project, userId)) {
            throw new AccessDeniedException(String.format("Access denied in project %s for user %s", project.getProject().getTitle(), userId));
        }
    }

    private boolean isEligible(ProjectResponseDto projectResponseDto, UUID userId) {
        return projectResponseDto.getProject().getUser().getId().equals(userId);
    }

}
