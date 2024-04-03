package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.repository.ToDoItemRepository;
import sofTodo.toDoList.domain.ToDoItem;

@Service
@RequiredArgsConstructor
public class ToDoService {
    private final ToDoItemRepository toDoItemRepository;

    public void saveToDoItem(String content) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setContent(content);
        toDoItemRepository.save(toDoItem);
    }

    public void EditToDoItem(String content, Long id) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setContent(content);
        toDoItem.setId(id);
        toDoItemRepository.save(toDoItem);
    }

}
