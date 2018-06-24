package us.martink.app.controllers.todo.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import us.martink.app.controllers.todo.service.TodoService;
import us.martink.bl.todo.model.TodoItemSearch;
import us.martink.bl.todo.model.TodoItemView;

/**
 * Created by tadas.
 */
@Controller
public class TodoController {

    private TodoService todoService;
    private TodoItemSearch search;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    public String getAll(Model model,
                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                         @RequestParam(value = "activePage", required = false, defaultValue = "1") Integer activePage) {
        initSearch(pageSize, activePage);
        search.setArchived(false);
        Page<TodoItemView> items = todoService.findAllBySearch(search);
        model.addAttribute("todos", items);
        model.addAttribute("search", search);
        return "index";
    }

    @RequestMapping(value = "/archived", method = RequestMethod.GET)
    public String getAllArchived(Model model,
                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                 @RequestParam(value = "activePage", required = false, defaultValue = "1") Integer activePage) {
        initSearch(pageSize, activePage);
        search.setArchived(true);
        Page<TodoItemView> items = todoService.findAllBySearch(search);
        model.addAttribute("todos", items);
        model.addAttribute("search", search);
        return "index";
    }

    @RequestMapping(value = "/markArchived/{id}/{archived}", method = RequestMethod.GET)
    public String markArchived(@PathVariable("id") Long id, @PathVariable("archived") Boolean archived) {
        if (id == null || archived == null) {
            //TODO validation
            return "redirect:/all";
        }
        todoService.markArchived(id, !archived);
        return archived ? "redirect:/archived" : "redirect:/all";
    }

    @RequestMapping(value = "/addTodo", method = RequestMethod.POST)
    public String addTodo(@RequestParam("todoText") String todoText) {
        if (StringUtils.isEmpty(todoText)) {
            //TODO validation
            return "redirect:/all";
        }
        todoService.addTodo(todoText);
        return "redirect:/all";
    }

    private void initSearch(Integer pageSize, Integer activePage) {
        if (search == null) {
            search = new TodoItemSearch();
        }
        search.setPageSize(pageSize);
        search.setActivePage(activePage);
    }
}
