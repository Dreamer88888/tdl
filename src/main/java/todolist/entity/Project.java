package todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import todolist.enums.Progress;

import java.util.UUID;

@Entity
@Table(name = "project")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private long createdAt;
    private long dueAt;
    private Progress progress;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "emma_user_id")
    private EmmaUser user;

}
