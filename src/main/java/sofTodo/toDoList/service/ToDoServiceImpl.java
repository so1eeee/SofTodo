package sofTodo.toDoList.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sofTodo.toDoList.domain.ToDoItem;
import sofTodo.toDoList.dto.AddToDoRequest;
import sofTodo.toDoList.dto.UpdateToDoRequest;
import sofTodo.toDoList.repository.ToDoItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl {
    private final ToDoItemRepository toDoItemRepository;



    public ToDoItem saveToDo(AddToDoRequest request) {
        return toDoItemRepository.save(request.toEntity());
    }
//
//    @Override
//    public void editToDoItem(String content, Long id) {
//        ToDoItem toDoItem = new ToDoItem();
//        toDoItem.setContent(content);
//        toDoItem.setId(id);
//        toDoItemRepository.save(toDoItem);
//    }
//
    public List<ToDoItem> findAll() {
        return toDoItemRepository.findAll();
    }

    public ToDoItem findById(Long id) {
        return toDoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(Long id) {
        toDoItemRepository.deleteById(id);
    }

    // 메칭한 메서드를 하나의 트랜잭션으로 묶는 역할을 함. 이렇게 하면 update() 메서드는 엔티티의 필드 값이 바뀌면 중간에 에러가 발생해도 제대로 된 값 수정을 보장하게 되어있음.
    @Transactional
    public ToDoItem update(Long id, UpdateToDoRequest request) {
        ToDoItem toDoItem = toDoItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        toDoItem.setContent(request.getContent());
        return toDoItem;
    }
//
//    @Override
//    public boolean editToDoItem(Model model, Long id) {
//        Optional<ToDoItem> result = toDoItemRepository.findById(id);
//        return toDoItemRepository.findById(id)
//                .map(item -> {
//                    model.addAttribute("data", item);
//                    return true;
//                })
//                .orElse(false);
//    }
//
//    @Override
//    public void deleteToDoItem(Long id) {
//        toDoItemRepository.deleteById(id);
//    }
//
//    public void delete(Long id) {
//        toDoItemRepository.deleteById(id);
//    }
}