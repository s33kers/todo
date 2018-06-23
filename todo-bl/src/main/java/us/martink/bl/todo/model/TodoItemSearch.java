package us.martink.bl.todo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tadas.
 */
@Getter
@Setter
public class TodoItemSearch {

    private boolean archived;
    private int activePage = 0;
    private int pageSize = 10;
}
