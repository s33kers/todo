package us.martink.app.controllers.todo.service;

import org.springframework.data.domain.Page;
import us.martink.bl.todo.model.TodoItemSearch;
import us.martink.bl.todo.model.TodoItemView;

/**
 * Created by tadas.
 */
public interface TodoService {
    Page<TodoItemView> findAllBySearch(TodoItemSearch search);
    void markArchived(Long id, Boolean archived);
}
