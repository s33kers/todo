package us.martink.app.controllers.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import us.martink.bl.todo.model.TodoItemSearch;
import us.martink.bl.todo.model.TodoItemView;
import us.martink.bl.todo.repsitory.TodoRepo;

import java.util.Objects;

/**
 * Created by tadas.
 */
@Component
public class TodoServiceImpl implements TodoService {

    private TodoRepo todoRepo;

    public TodoServiceImpl(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    @Override
    public Page<TodoItemView> findAllBySearch(TodoItemSearch search) {
        Objects.requireNonNull(search, "Search must be not null");
        return todoRepo.findAllBySearch(search);
    }

    @Override
    public void markArchived(Long id, Boolean archived) {
        todoRepo.markArchived(id, archived);
    }
}
