package sofTodo.toDoList.repsoitory;

import org.springframework.stereotype.Repository;
import sofTodo.toDoList.domain.Todo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class TodoRepository {
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Todo todo : todolist) {
            builder.append(todo.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    private final List<Todo> todolist;
    public TodoRepository(List<Todo> todolist) {
        this.todolist = new ArrayList<Todo>();
    }

    public Todo save(Todo todo) {
        todolist.add(todo);
        System.out.println("todolist = " + todolist.toString());
        return todo;
    }

    public List<Todo> findAllTodo() {
        System.out.println("todolist = " + todolist.toString());
        return todolist;
    }

    public void update(Long id, String content) {
        delete(id);
        Todo newTodo = new Todo(id,content);
        todolist.add(newTodo);
    }

    public void delete(Long id) {
        Iterator<Todo> iterator = todolist.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
                break;
            }
        }
        System.out.println("todolist = " + todolist.toString());
    }
}
