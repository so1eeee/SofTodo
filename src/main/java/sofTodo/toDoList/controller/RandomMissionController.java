package sofTodo.toDoList.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sofTodo.toDoList.service.RandomMissionService;

@Controller
@RequiredArgsConstructor
public class RandomMissionController {
    private final RandomMissionService randomMissionService;

    @GetMapping("/missions")
    public String getRandomMissions(Model model) {
        model.addAttribute("missions", randomMissionService.findAll());
        return "missions";
    }
}
