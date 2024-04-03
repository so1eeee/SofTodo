package sofTodo.toDoList.service;

import org.springframework.ui.Model;

public interface ToDoService {
    void addToDoItem(String content);

    void editToDoItem(String content, Long id);

    void findAll(Model model);

    int editToDoItem(Model model, Long id);


}
