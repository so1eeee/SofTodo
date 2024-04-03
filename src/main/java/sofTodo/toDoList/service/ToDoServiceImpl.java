package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.repository.ToDoItemRepository;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {
    private final ToDoItemRepository toDoItemRepository;

    @Override
    public void addToDoItem(String content) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setContent(content);
        toDoItemRepository.save(toDoItem);
    }

    @Override
    public void editToDoItem(String content, Long id) {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setContent(content);
        toDoItem.setId(id);
        toDoItemRepository.save(toDoItem);
    }
}