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
@Table(name = "todolist")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDoList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Progress progress;
    private String description;
    private long date;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private Project project;

}
