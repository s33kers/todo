package us.martink.app.controllers.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import us.martink.bl.todo.model.TodoItemSearch;
import us.martink.bl.todo.model.TodoItemView;
import us.martink.bl.todo.repsitory.TodoRepo;
import us.martink.model.todo.TodoItem;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * Created by tadas.
 */
@Component
@Transactional
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

    @Override
    public void addTodo(String todoText) {
        Objects.requireNonNull(todoText, "Todotext must be not null");

        TodoItem todoItem = new TodoItem();
        todoItem.setContent(todoText);
        todoRepo.save(todoItem);
    }
}
