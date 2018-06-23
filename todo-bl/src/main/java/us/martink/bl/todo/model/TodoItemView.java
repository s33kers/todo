package us.martink.bl.todo.model;

import lombok.Getter;
import lombok.Setter;
import us.martink.bl.utils.CoreUtils;

import java.time.LocalDateTime;

/**
 * Created by tadas.
 */
@Setter
@Getter
public class TodoItemView {

    private Long id;
    private String created;
    private String content;
    private boolean archived;

    public TodoItemView(Long id, LocalDateTime created, String content, boolean archived) {
        this.id = id;
        this.created = CoreUtils.FORMATTER_DATE.format(created);
        this.content = content;
        this.archived = archived;
    }

}
