package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.repository.ToDoItemRepository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void findAll(Model model) {
        List<ToDoItem> result = toDoItemRepository.findAll();
        model.addAttribute("todoitems", result);
    }

    @Override
    public int editToDoItem(Model model, Long id) {
        Optional<ToDoItem> result = toDoItemRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return 1;
        } else {
            return 0;
        }
    }
}