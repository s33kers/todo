package us.martink.bl.todo.repsitory;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import us.martink.bl.todo.model.TodoItemSearch;
import us.martink.bl.todo.model.TodoItemView;
import us.martink.model.todo.TodoItem;

/**
 * Created by tadas.
 */
public interface TodoRepo extends JpaRepository<TodoItem, Long> {
    Page<TodoItemView> findAllBySearch(TodoItemSearch search);
    void markArchived(Long id, Boolean archived);
}
