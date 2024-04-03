package sofTodo.toDoList.service;

public interface ToDoService {
    void addToDoItem(String content);

    void editToDoItem(String content, Long id);

}
