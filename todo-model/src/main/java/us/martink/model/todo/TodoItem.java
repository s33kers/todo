package us.martink.model.todo;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
public class TodoItem {

    private Long id;
    private LocalDateTime created = LocalDateTime.now();
    private String content;
    private boolean archived;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(name = "CREATED", nullable = false)
    public LocalDateTime getCreated() {
        return created;
    }

    @Column(name = "CONTENT", nullable = false, length = 4000)
    public String getContent() {
        return content;
    }

    @Column(name = "ARCHIVED", nullable = false)
    public boolean isArchived() {
        return archived;
    }
}
